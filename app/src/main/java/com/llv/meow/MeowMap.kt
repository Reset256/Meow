package com.llv.meow

class MeowMap {

    private val map = HashMap<Mood, Pair<Int, Int>>()

    fun addRes(mood: Mood, picId: Int, audioId: Int) {
        map[mood] = Pair(picId, audioId)
    }

    fun getPicId(mood: Mood) = map[mood]?.first!!

    fun getAudioId(mood: Mood) = map[mood]?.second

}