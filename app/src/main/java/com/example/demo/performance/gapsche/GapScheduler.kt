package com.ss.android.ugc.aweme.im.sdk.chat.preload

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.os.Trace
import android.util.Log
import android.view.Choreographer
import android.view.Display
import android.view.View
import android.view.View.OnAttachStateChangeListener
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnDrawListener
import android.view.ViewTreeObserver.OnPreDrawListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.system.measureTimeMillis

class MaxSizeConcurrentLinkedQueue<T>(private val capacity: Int) : ConcurrentLinkedQueue<T>() {
    override fun offer(element: T): Boolean {
        if (size + 1 > capacity) {
            super.poll()
        }
        return super.offer(element)
    }
}

interface Scheduler : Runnable {
    /**
     * 当一帧来的时候调用，比如onscrolled回调
     */
    fun schedule(iTasks: List<ITask>)
}

interface ITask {
    fun call()

    fun name(): String

}

interface SystemTime {
    fun getTimeMills(): Long
}
object FramePerfCollector {
    const val TAG = "FramePerfCollector"
    var running = false
    private var frameTime: Long = 0
    private val frameTimeNanosList by lazy { LinkedList<Long>() }
    private val frameSize = 60
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private val avgFrameTimeList = mutableListOf<Long>()
    private val avgCpuRateList = mutableListOf<Double>()
    private var subframeCount = 0


    private val frameCallback by lazy {
        object : Choreographer.FrameCallback {
            override fun doFrame(frameTimeNanos: Long) {
                if (!running) return
                subframeCount += 1
                frameTimeNanosList.add(frameTimeNanos)
                if (frameTimeNanosList.size > frameSize) {
                    frameTime =
                        (frameTimeNanosList.last - frameTimeNanosList.first) / (frameTimeNanosList.size - 1)
                    frameTimeNanosList.clear()
                    Log.i(TAG, "avg frameTime = $frameTime")
                    if (frameTime != 0L) {
                        avgFrameTimeList.add(frameTime)
                    }
                }
                if (System.currentTimeMillis() - startMs < MAX_RUNNING_TIME_MS) {
                    Choreographer.getInstance().postFrameCallback(this)
                } else {
                    // 这里可能size = 1 有异常
                    if (frameTimeNanosList.size > 1) {
                        frameTime =
                            (frameTimeNanosList.last - frameTimeNanosList.first) / (frameTimeNanosList.size - 1)
                        frameTimeNanosList.clear()
                        Log.d(
                            TAG,
                            "avg frameTime = $frameTime} "
                        )
                        if (frameTime != 0L) {
                            avgFrameTimeList.add(frameTime)
                        }
                    }
                    destory()
                }
            }
        }
    }

    private var startMs = 0L
    private var MAX_RUNNING_TIME_MS = 80 * 1000
    fun stop() {
        Log.i(
            TAG,
            "total avg frametime ${avgFrameTimeList.average()} " +
                    "subframeCount = ${subframeCount} " +
                    "cost = ${System.currentTimeMillis() - startMs} avgCpuRate = ${avgCpuRateList.average()}"
        )
        subframeCount = 0
        running = false
    }

    private var dead = false
    fun init() {
        dead = false
        subframeCount = 0
        running = false
        avgFrameTimeList.clear()
        frameTimeNanosList.clear()
    }

    fun destory() {
        stop()
        dead = true
        avgFrameTimeList.clear()
        frameTimeNanosList.clear()
    }

    fun start() {
        if (running || dead) return
        startMs = System.currentTimeMillis()
        running = true
        frameTimeNanosList.clear()
        Choreographer.getInstance().postFrameCallback(frameCallback)
    }
}
class GapScheduler(val gapView: View) : Scheduler, OnPreDrawListener {
    companion object {
        const val TAG = "GapScheduler"
    }

    private val tasks: MutableList<ITask> by lazy {
        mutableListOf()
    }

    private var frameIntervalMs: Long = -1L

    /**
     * 需要线程安全
     */
    private val costWindow = ConcurrentHashMap<String, MaxSizeConcurrentLinkedQueue<Long>>()
    private var mainCallCount = 0
    private var subCallCount = 0
    private var postCount = 0
    private var avgCostMap = ConcurrentHashMap<String, Long>()
    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) {

        }

        override fun onDisplayChanged(displayId: Int) {
            frameIntervalMs = getFrameIntervalMs()
        }

        override fun onDisplayRemoved(displayId: Int) {

        }
    }
    init {
        val displayManager = gapView.context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager

        gapView.addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                frameIntervalMs = getFrameIntervalMs()
                displayManager.registerDisplayListener(displayListener, null)
            }

            override fun onViewDetachedFromWindow(v: View) {
                gapView.removeCallbacks(this@GapScheduler)
                gapView.viewTreeObserver.removeOnPreDrawListener(this@GapScheduler)
                displayManager.unregisterDisplayListener(displayListener)
            }
        })
    }

    private fun getFrameIntervalMs(): Long {
        Log.i(TAG, "getFrameIntervalMs: ")
        val display: Display = gapView.display
        var refreshRate = 60.0f
        if (display != null) {
            val displayRefreshRate: Float = display.refreshRate
            if (displayRefreshRate >= 30.0f) {
                refreshRate = displayRefreshRate
            }
        }
        return (1000 / refreshRate).toLong()
    }

    /**
     * 下一帧的预测值，从另一角度看是 任务做完的 截止时间戳
     */
    private var deadLineMills: Long = 0L

    private var overTaskCount = 0
    private var overFrameMsCount = 0
    private var overPostCount = 0
    private var overDeadLineCount = 0

    private val needPostRunTasks = mutableListOf<ITask>()
    override fun schedule(iTasks: List<ITask>) {
        if (frameIntervalMs == -1L) {
            Log.d(TAG, "frameIntervalMs")
            frameIntervalMs = getFrameIntervalMs()
            return
        }
//        if (ImConvPagePerfOpt1Setting.instantUseThreadHanTask) {
//            if (postCount > ImConvPagePerfOpt1Setting.maxPostNum) {
//                runTaskOnSingleThread((tasks + iTasks).map { it })
//                tasks.clear()
//                return
//            }
//            // tasks任务堆积 任务平均时间 赶快用子线程消化掉
//            if (tasks.size > ImConvPagePerfOpt1Setting.maxTaskNum) {
//                overTaskCount += iTasks.size
////            IMLog.d("logicCheck task more than maxTaskNum")
//                runTaskOnSingleThread((tasks + iTasks).map { it })
//                tasks.clear()
//                return
//            }
//        }
        //前置检查任务的平均时间是否>16
        val preCheckedTime = (tasks + iTasks).sumOf {
            avgCostMap[it.name()] ?: 0L
        }
        if (preCheckedTime > frameIntervalMs) {
            overFrameMsCount += iTasks.size
            runTaskOnSingleThread((tasks + iTasks).map { it })
            tasks.clear()
            return
        }
//        // 这个参数不在子线程运行，所以可以考虑全局变量，对于频繁调用 不用重新new
//        needPostRunTasks.clear()
//        // 这个参数要在子线程运行，所以用local
//        val subThreadRunTasks = mutableListOf<ITask>()
//        iTasks.forEach {
//            // 没有预测值，先用子线程处理记录预测值
//            if (!costWindow.containsKey(it.name())) {
//                subThreadRunTasks.add(it)
//            } else {
//                val costWSize = costWindow[it.name()]?.size ?: 0
//                // cost window size 小于 容量值，也用子线程执行
//                if (costWSize < 5) {
//                    subThreadRunTasks.add(it)
//                } else {
//                    needPostRunTasks.add(it)
//                }
//            }
//        }
//        if (subThreadRunTasks.isNotEmpty()) {
//            runTaskOnSingleThread(subThreadRunTasks)
//        }
//        if (needPostRunTasks.isEmpty()) return
        tasks.addAll(iTasks)
        val upTime = SystemClock.uptimeMillis()
        deadLineMills = upTime + frameIntervalMs
//        val drawingTimeMills = gapView.drawingTime
//        Log.d(TAG, "schedule: drawingTimeMills = $drawingTimeMills")
        // 尽量 保证是一次drawframe的 最后一个post
        gapView.viewTreeObserver.addOnPreDrawListener(this)
    }

    override fun onPreDraw(): Boolean {
        kotlin.runCatching {
            postCount++
            gapView.postDelayed(this, 0)
            gapView.viewTreeObserver.removeOnPreDrawListener(this)
        }.getOrElse {
        }
        return true
    }

    private fun runTaskOnSingleThread(localTasks: List<ITask>) {
        GlobalScope.launch {
            localTasks.forEach { t ->
                subCallCount++
                val cost = measureTimeMillis {
                    t.call()
                }
                recordCost(t.name(), cost)
            }
        }
    }


    // 如果预测时间一直大于帧返回的时间怎么办
    /**
     * TODO 这个算法有很多可优化的点
     */
    override fun run() {
        postCount--
        if (tasks.isEmpty()) return
        // 预测任务花费时间
        var expectCost = 0L
        tasks.forEach {
            expectCost += avgCostMap[it.name()] ?: 0
        }
        val drawingTimeMills = gapView.drawingTime

//        Log.d(TAG, "drawingTimeMills = ${drawingTimeMills}")
        val newDeadLine = drawingTimeMills + frameIntervalMs
        val upTimeMs = SystemClock.uptimeMillis()
//        Log.d(TAG, "run: newDeadLine = $newDeadLine deadLineMills = ${deadLineMills} upTimeMs = ${upTimeMs}")
        deadLineMills = minOf(newDeadLine, deadLineMills)

        /**
         * 任务完成的时间戳
         */
        val expectTaskDone = upTimeMs + expectCost
        if (expectTaskDone < deadLineMills) {
            Log.d(TAG, "logicCheck gap run ")
            tasks.forEach {
                mainCallCount++
                val cost = measureTimeMillis {
                    it.call()
                }
                recordCost(it.name(), cost)
            }
            if (SystemClock.uptimeMillis() > deadLineMills) {
                overDeadLineCount++
            }
        } else {
            // TODO 看看这里可以做些什么优化，放到子线程？需要定义一个参数表明什么才是耗时call。建模？
            // check下是任务太重，还是UI线程 忙碌
            val delta = expectTaskDone - deadLineMills
            Log.d(TAG, "logicCheck gap not run due to $delta ")
            overPostCount++
            runTaskOnSingleThread(tasks.map { it })
        }
        tasks.clear()
    }

    fun updateCostAvg(name: String) {
        avgCostMap[name] = costWindow[name]?.average()?.toLong() ?: 0L
    }

    private fun recordCost(name: String, cost: Long) {
        if (cost == 0L) return
        if (!costWindow.containsKey(name)) {
            costWindow[name] =
                MaxSizeConcurrentLinkedQueue(10)
        }
        costWindow[name]?.offer(cost)
        updateCostAvg(name)
    }
}