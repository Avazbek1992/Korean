package uz.best.korean

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import uz.best.korean.databinding.ActivityMainBinding
import uz.best.korean.model.TestModel
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var questionList = ArrayList<TestModel>()
    private lateinit var binding: ActivityMainBinding
    private var testPosition = 0
    private var countCurrentAnswer = 0
    private var animResult: FrameLayout? = null
    private var action = true
    private var volumeCounter = 0
    private var time = 0
    private val newList = ArrayList<TestModel>()
    private val imageList = ArrayList<Int>()
    private val newImageList = ArrayList<Int>()

    //images
    private var defaultImageList = arrayListOf(
        R.drawable.img,
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_4,
        R.drawable.img_5,
        R.drawable.img_6,
        R.drawable.img_7,
        R.drawable.img_8,
        R.drawable.img_9,
        R.drawable.img_10,
        R.drawable.img_11,
        R.drawable.img_12,
        R.drawable.img_13,
        R.drawable.img_14,
        R.drawable.img_15,
        R.drawable.img_16,
        R.drawable.img_17,
        R.drawable.img_18,
        R.drawable.img_19,
        R.drawable.img_20,
        R.drawable.img_21,
        R.drawable.img_22,
        R.drawable.img_23,
        R.drawable.img_24,
        R.drawable.img_25,
        R.drawable.img_26,
        R.drawable.img_27,
        R.drawable.img_28,
        R.drawable.img_29,
        R.drawable.img_30,
        R.drawable.img_31,
        R.drawable.img_32,
        R.drawable.img_33,
        R.drawable.img_34,
        R.drawable.img_35,
        R.drawable.img_36,
        R.drawable.img_37,
        R.drawable.img_38,
        R.drawable.img_39,
        R.drawable.img_40,
        R.drawable.img_41,
        R.drawable.img_42,
        R.drawable.img_43,
        R.drawable.img_44,
        R.drawable.img_45,
        R.drawable.img_46,
        R.drawable.img_47,
        R.drawable.img_48,
        R.drawable.img_49,
        R.drawable.img_50,
        R.drawable.img_51,
        R.drawable.img_52,
        R.drawable.img_53,
        R.drawable.img_54,
        R.drawable.img_55,
        R.drawable.img_56,
        R.drawable.img_57,
        R.drawable.img_58,
        R.drawable.img_59,
        R.drawable.img_60,
        R.drawable.img_61,
        R.drawable.img_62,
        R.drawable.img_63,
        R.drawable.img_64,
        R.drawable.img_65,
        R.drawable.img_66,
        R.drawable.img_67,
        R.drawable.img_68,
        R.drawable.img_69,
        R.drawable.img_70,
        R.drawable.img_71,
        R.drawable.img_72,
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.quizStart.setOnClickListener {
            start()
        }
        binding.tvCounter.text = "01"

        binding.imgVolume.setOnClickListener {
            if (volumeCounter % 2 == 0) {
                binding.imgVolume.setImageResource(R.drawable.ic_volume_off)
            } else {
                binding.imgVolume.setImageResource(R.drawable.ic_volume_up)
            }
            volumeCounter++
        }

        binding.imgBack.setOnClickListener {
            if (action) {
                if (testPosition > 0) {
                    --testPosition
                    setQuestion(testPosition)
                    binding.tvCounter.text = if (testPosition < 9) {
                        "0${testPosition + 1}"
                    } else {
                        (testPosition + 1).toString()
                    }
                    animResult?.clearAnimation()
                    clear()
                }
            }
        }

        binding.imgNext.setOnClickListener {
            if (action) {
                if (testPosition < 72) {
                    ++testPosition
                    setQuestion(testPosition)
                    binding.tvCounter.text = if (testPosition < 9) {
                        "0${testPosition + 1}"
                    } else {
                        (testPosition + 1).toString()
                    }
                    animResult?.clearAnimation()
                    clear()
                }
            }
        }

        binding.tvA.setOnClickListener {
            if (action)
                if (questionList[testPosition].getAnswerCorrect().isEmpty()) {
                    action = false
                    checking(
                        binding.imgA,
                        binding.imgResultA,
                        questionList[testPosition].getAnswerA(),
                        1
                    )
                }
        }

        binding.tvB.setOnClickListener {
            if (action)
                if (questionList[testPosition].getAnswerCorrect().isEmpty()) {
                    action = false
                    checking(
                        binding.imgB,
                        binding.imgResultB,
                        questionList[testPosition].getAnswerB(),
                        2
                    )
                }
        }

        binding.tvC.setOnClickListener {
            if (action)
                if (questionList[testPosition].getAnswerCorrect().isEmpty()) {
                    action = false
                    checking(
                        binding.imgC,
                        binding.imgResultC,
                        questionList[testPosition].getAnswerC(),
                        3
                    )
                }
        }

        binding.tvD.setOnClickListener {
            if (action)
                if (questionList[testPosition].getAnswerCorrect().isEmpty()) {
                    action = false
                    checking(
                        binding.imgD,
                        binding.imgResultD,
                        questionList[testPosition].getAnswerD(),
                        4
                    )
                }
        }
    }

    private fun start() {
        readFromAsset()
        setTime()
        Thread {
            val anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
            binding.startLayout.startAnimation(anim)
            Thread.sleep(2000)
            runOnUiThread {
                binding.startLayout.visibility = View.GONE
            }
        }.start()
    }

    private fun clearResult() {
        binding.imgResultA.visibility = View.GONE
        binding.imgResultB.visibility = View.GONE
        binding.imgResultC.visibility = View.GONE
        binding.imgResultD.visibility = View.GONE
    }

    private fun clear() {
        clearResult()
        if (questionList[testPosition].getAnswerCorrect().isNotEmpty()) {
            when (questionList[testPosition].getAnswerCorrect()) {
                "1" -> {
                    animResult = binding.layoutA
                    binding.imgResultA.setImageResource(R.drawable.ic_correct)
                    binding.imgResultA.visibility = View.VISIBLE
                }

                "2" -> {
                    animResult = binding.layoutB
                    binding.imgResultB.setImageResource(R.drawable.ic_correct)
                    binding.imgResultB.visibility = View.VISIBLE
                }
                "3" -> {
                    animResult = binding.layoutC
                    binding.imgResultC.setImageResource(R.drawable.ic_correct)
                    binding.imgResultC.visibility = View.VISIBLE
                }
                "4" -> {
                    animResult = binding.layoutD
                    binding.imgResultD.setImageResource(R.drawable.ic_correct)
                    binding.imgResultD.visibility = View.VISIBLE
                }
            }
            val anim = AnimationUtils.loadAnimation(this, R.anim.yes_answer_anim)
            if (questionList[testPosition].getAnswerWrong().isNotEmpty()) {
                when (questionList[testPosition].getAnswerWrong()) {
                    "1" -> {
                        binding.imgResultA.setImageResource(R.drawable.ic_wrong)
                        binding.imgResultA.visibility = View.VISIBLE
                        animResult = binding.layoutA
                    }

                    "2" -> {
                        binding.imgResultB.setImageResource(R.drawable.ic_wrong)
                        binding.imgResultB.visibility = View.VISIBLE
                        animResult = binding.layoutB
                    }
                    "3" -> {
                        binding.imgResultC.setImageResource(R.drawable.ic_wrong)
                        binding.imgResultC.visibility = View.VISIBLE
                        animResult = binding.layoutC
                    }
                    "4" -> {
                        binding.imgResultD.setImageResource(R.drawable.ic_wrong)
                        binding.imgResultD.visibility = View.VISIBLE
                        animResult = binding.layoutD
                    }
                }
            }
            if (questionList[testPosition].getAnswerCorrect().isNotEmpty()) {
                animResult?.startAnimation(anim)
            }
        }
    }

    private fun checking(
        img: ImageView, imgResult: ImageView, answer: String, answerPosition: Int
    ) {
        if (answer.contains("+")) {
            binding.tvCurrent.text = "${++countCurrentAnswer}"
            questionList[testPosition].setAnswerCorrect("$answerPosition")
            correctAnim(img, imgResult)
        } else {
            questionList[testPosition].setAnswerWrong("$answerPosition")
            wrongAnim(img, imgResult)
        }
    }

    private fun checkComplete() {
        var checked = true
        for (i in 0 until 73) {
            if (questionList[i].getAnswerCorrect().isEmpty()) {
                checked = false
                break
            }
        }

        if (checked) {
            val newDialog = newDialog()
            newDialog.show()
        } else {
            Thread {
                Thread.sleep(2000)
                runOnUiThread {
                    if (testPosition < 72) {
                        ++testPosition
                        setQuestion(testPosition)
                        binding.tvCounter.text = if (testPosition < 9) {
                            "0${testPosition + 1}"
                        } else {
                            (testPosition + 1).toString()
                        }
                        animResult?.clearAnimation()
                        clear()
                    }
                }
            }.start()
        }

    }

    private fun correctAnim(img: ImageView, imgResult: ImageView) {
        if (volumeCounter % 2 == 0) {
            val player = MediaPlayer.create(this, R.raw.correct_audio)
            player.start()
        }
        img.visibility = View.VISIBLE
        img.setImageResource(R.drawable.ic_correct)
        val anim = AnimationUtils.loadAnimation(this, R.anim.correct_anim)
        img.startAnimation(anim)
        Thread {
            Thread.sleep(700)
            runOnUiThread {
                img.visibility = View.GONE
                imgResult.setImageResource(R.drawable.ic_correct)
                imgResult.visibility = View.VISIBLE
                action = true
                checkComplete()
            }
        }.start()

    }

    private fun wrongAnim(img: ImageView, imgResult: ImageView) {
        if (volumeCounter % 2 == 0) {
            val player = MediaPlayer.create(this, R.raw.wrogn_audio)
            player.start()
        }
        img.visibility = View.VISIBLE
        img.setImageResource(R.drawable.ic_wrong)
        val anim = AnimationUtils.loadAnimation(this, R.anim.wroing_anim)
        img.startAnimation(anim)
        Thread {
            Thread.sleep(700)
            runOnUiThread {
                img.visibility = View.GONE
                imgResult.setImageResource(R.drawable.ic_wrong)
                imgResult.visibility = View.VISIBLE
            }
            Thread.sleep(1000)
            runOnUiThread {
                val animSmall = AnimationUtils.loadAnimation(this, R.anim.wroing_anim)
                if (questionList[testPosition].getAnswerA().contains("+")) {
                    questionList[testPosition].setAnswerCorrect("1")
                    binding.imgResultA.visibility = View.VISIBLE
                    binding.imgResultA.setImageResource(R.drawable.ic_correct)
                    binding.imgResultA.startAnimation(animSmall)
                } else if (questionList[testPosition].getAnswerB().contains("+")) {
                    questionList[testPosition].setAnswerCorrect("2")
                    binding.imgResultB.visibility = View.VISIBLE
                    binding.imgResultB.setImageResource(R.drawable.ic_correct)
                    binding.imgResultB.startAnimation(animSmall)
                } else if (questionList[testPosition].getAnswerC().contains("+")) {
                    questionList[testPosition].setAnswerCorrect("3")
                    binding.imgResultC.visibility = View.VISIBLE
                    binding.imgResultC.setImageResource(R.drawable.ic_correct)
                    binding.imgResultC.startAnimation(animSmall)
                } else if (questionList[testPosition].getAnswerD().contains("+")) {
                    questionList[testPosition].setAnswerCorrect("4")
                    binding.imgResultD.visibility = View.VISIBLE
                    binding.imgResultD.setImageResource(R.drawable.ic_correct)
                    binding.imgResultD.startAnimation(animSmall)
                }
                action = true
                checkComplete()
            }
        }.start()

    }

    private fun setTime() {
        Thread {
            while (true) {
                Thread.sleep(1000)
                runOnUiThread {
                    binding.tvTime.text = getTime(time)
                }
                time++
            }
        }.start()
    }

    private fun getTime(time: Int): String {
        val minute = if (time / 60 < 10) {
            "0${time / 60}"
        } else {
            "${time / 60}"
        }
        val second = if (time % 60 < 10) {
            "0${time % 60}"
        } else {
            "${time % 60}"
        }
        return "$minute:$second"
    }

    private fun setQuestion(position: Int) {
        if (questionList[position].getQuestion().isNotEmpty())
            binding.tvQuestion.text = questionList[position].getQuestion().substring(1)
        if (questionList[position].getAnswerA().isNotEmpty())
            binding.tvA.text = questionList[position].getAnswerA().substring(1).trim()
        if (questionList[position].getAnswerB().isNotEmpty())
            binding.tvB.text = questionList[position].getAnswerB().substring(1).trim()
        if (questionList[position].getAnswerC().isNotEmpty())
            binding.tvC.text = questionList[position].getAnswerC().substring(1).trim()
        if (questionList[position].getAnswerD().isNotEmpty())
            binding.tvD.text = questionList[position].getAnswerD().substring(1).trim()
        binding.imageId.setImageResource(imageList[position])
    }

    private fun readFromAsset() {
        var k = 0
        val fileName = "test.txt"
        val bufferReader = application.assets.open(fileName).bufferedReader()
        var testModel = TestModel()
        questionList.clear()
        bufferReader.forEachLine { item ->
            println("DATA: $item")
            if (item.trim().isNotEmpty()) {
                val data = replaceEquals(item)
                println("DATA: $data")
                if (data.trim()[0] == '?') {
                    if (k != 0) {
                        questionList.add(testModel)
                        testModel = TestModel()
                    }
                    k = 0
                    testModel.setQuestion(data.trim())
                } else if (data.trim()[0] == '=' || data.trim()[0] == '+') {
                    k++
                    when (k) {
                        1 -> testModel.setAnswerA(data.trim())
                        2 -> testModel.setAnswerB(data.trim())
                        3 -> testModel.setAnswerC(data.trim())
                        4 -> testModel.setAnswerD(data.trim())
                    }
                }
            }
        }

        if (k != 0) {
            questionList.add(testModel)
            testModel = TestModel()
        }
        randomQuestion()
    }

    private fun replaceEquals(item: String): String {
//        var data = item.trim()
//        if (data[0] == '=') {
//            data = data.replaceFirst("=", "*")
//            data = data.replace("=", ",")
//            data = data.replaceFirst("*", "=")
//        } else {
//            data = data.replace("=", "")
//        }
        return item.trim()
    }

    private fun randomQuestion() {
        newList.clear()
        newImageList.clear()
        newList.addAll(questionList)
        newImageList.addAll(defaultImageList)
        println("QUESTION SIZE: ${newList.size}")
        println("questionList SIZE: ${questionList.size}")
        println("imageList SIZE: ${imageList.size}")
        println()
        imageList.clear()
        questionList = ArrayList()
        for (i in 0 until 73) {
            val randomIndex = Random.Default.nextInt(newList.size)
            val model = TestModel()
            val arrRandom = arrayListOf(
                newList[randomIndex].getAnswerA(),
                newList[randomIndex].getAnswerB(),
                newList[randomIndex].getAnswerC(),
                newList[randomIndex].getAnswerD()
            )
            model.setQuestion(newList[randomIndex].getQuestion())
            newList.remove(newList[randomIndex])
            val i1 = Random.Default.nextInt(arrRandom.size)
            model.setAnswerA(arrRandom[i1])
            arrRandom.remove(arrRandom[i1])
            val i2 = Random.Default.nextInt(arrRandom.size)
            model.setAnswerB(arrRandom[i2])
            arrRandom.remove(arrRandom[i2])
            val i3 = Random.Default.nextInt(arrRandom.size)
            model.setAnswerC(arrRandom[i3])
            arrRandom.remove(arrRandom[i3])
            val i4 = Random.Default.nextInt(arrRandom.size)
            model.setAnswerD(arrRandom[i4])
            arrRandom.remove(arrRandom[i4])
            questionList.add(model)
            imageList.add(newImageList[randomIndex])
            newImageList.remove(newImageList[randomIndex])
        }

        setQuestion(testPosition)
    }

    @SuppressLint("SetTextI18n")
    private fun newDialog(): Dialog {
        println("DIALOG")
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.findViewById<TextView>(R.id.id_score).text =
            "$countCurrentAnswer / 73"
        dialog.findViewById<TextView>(R.id.id_time).text =
            getTime(time)
        dialog.findViewById<TextView>(R.id.start_again)
            .setOnClickListener {
                time = 0
                countCurrentAnswer = 0
                binding.tvCounter.text = "01"
                binding.tvCurrent.text = "0"
                testPosition = 0
                readFromAsset()
                clear()
                dialog.dismiss()

            }
        return dialog
    }
}