package com.jatinvashisht.mergesortvisualizer.util

import javax.inject.Inject

class MergesortHelper @Inject constructor() {
    suspend fun mergeSort(list: List<Int>, onListCreated: (List<Int>) -> Unit): List<Int> {
        if (list.size <= 1) {
            return list
        }

        val middle = list.size / 2
        var left = list.subList(0,middle);
        var right = list.subList(middle,list.size);

        return merge(mergeSort(left, onListCreated), mergeSort(right, onListCreated)){
            onListCreated(it)
        }
    }

    suspend fun merge(left: List<Int>, right: List<Int>, onListCreated: (List<Int>) -> Unit): List<Int>  {
        var indexLeft = 0
        var indexRight = 0
        var newList : MutableList<Int> = mutableListOf()

        while (indexLeft < left.count() && indexRight < right.count()) {
            if (left[indexLeft] <= right[indexRight]) {
                newList.add(left[indexLeft])
                indexLeft++
            } else {
                newList.add(right[indexRight])
                indexRight++
            }
        }

        while (indexLeft < left.size) {
            newList.add(left[indexLeft])
            indexLeft++
        }

        while (indexRight < right.size) {
            newList.add(right[indexRight])
            indexRight++
        }
        onListCreated(newList)
        return newList;
    }
}