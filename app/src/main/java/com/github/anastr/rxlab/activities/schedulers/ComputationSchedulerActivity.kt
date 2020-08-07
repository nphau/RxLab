package com.github.anastr.rxlab.activities.schedulers

import android.os.Bundle
import com.github.anastr.rxlab.activities.OperationActivity
import com.github.anastr.rxlab.objects.drawing.FixedEmitsOperation
import com.github.anastr.rxlab.objects.drawing.ObserverObject
import com.github.anastr.rxlab.objects.emits.BallEmit
import com.github.anastr.rxlab.view.Action
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_operation.*

class ComputationSchedulerActivity: OperationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCode("Observable.just(\"A\")\n" +
                "        .observeOn(Schedulers.computation())\n" +
                "        .subscribe();")


        val a = BallEmit("A")

        val justOperation = FixedEmitsOperation("just", listOf(a))
        surfaceView.addDrawingObject(justOperation)
        val observerObject = ObserverObject("Observer")
        surfaceView.addDrawingObject(observerObject)

        val actions = ArrayList<Action>()

        Observable.just(a)
            .observeOn(Schedulers.computation())
            .subscribe({
                val thread = Thread.currentThread().name
                actions.add(Action(1000) {
                    it.checkThread(thread)
                    moveEmit(it, justOperation, observerObject)
                })
            }, errorHandler, {
                actions.add(Action(0) { doOnRenderThread { observerObject.complete() } })
                surfaceView.actions(actions)
            })
            .disposeOnDestroy()
    }
}
