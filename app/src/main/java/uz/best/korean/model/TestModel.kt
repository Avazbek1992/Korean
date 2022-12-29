package uz.best.korean.model

import uz.best.korean.R

class TestModel {
    private var question: String = ""
    private var answerA: String = ""
    private var answerB: String = ""
    private var answerC: String = ""
    private var answerD: String = ""
    private var answerCorrect: String = ""
    private var answerWrong: String = ""
    private var imageId: Int = R.drawable.img

    fun setQuestion(question: String) {
        this.question = question
    }

    fun setAnswerA(answerA: String) {
        this.answerA = answerA
    }

    fun setAnswerB(answerB: String) {
        this.answerB = answerB
    }

    fun setAnswerC(answerC: String) {
        this.answerC = answerC
    }

    fun setAnswerD(answerD: String) {
        this.answerD = answerD
    }

//    fun setImageID(imageId: Int) {
//        this.imageId = imageId
//    }
//
//    fun getImageID(): Int {
//        return imageId
//    }

    fun setAnswerWrong(answerWrong: String) {
        this.answerWrong = answerWrong
    }

    fun setAnswerCorrect(answerCorrect: String) {
        this.answerCorrect = answerCorrect
    }

    fun getQuestion(): String {
        return this.question
    }

    fun getAnswerA(): String {
        return this.answerA
    }

    fun getAnswerB(): String {
        return this.answerB
    }

    fun getAnswerC(): String {
        return this.answerC
    }

    fun getAnswerD(): String {
        return this.answerD
    }

    fun getAnswerWrong(): String {
        return this.answerWrong
    }

    fun getAnswerCorrect(): String {
        return this.answerCorrect
    }
}