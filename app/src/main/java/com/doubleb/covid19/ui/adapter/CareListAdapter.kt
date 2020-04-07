package com.doubleb.covid19.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleb.covid19.R
import com.doubleb.covid19.ui.view_holder.CareViewHolder
import com.doubleb.covid19.ui.view_holder.PreventionsViewHolder
import com.doubleb.covid19.ui.view_holder.SymptomsViewHolder

class CareListAdapter<T : Enum<T>>(private val enumList: Array<T>) :
    RecyclerView.Adapter<CareViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (enumList.isArrayOf<Preventions>()) {
            PreventionsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_holder_prevention,
                    parent,
                    false
                )
            )
        } else {
            SymptomsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_holder_symptoms,
                    parent,
                    false
                )
            )
        }

    override fun getItemCount() = enumList.size

    override fun onBindViewHolder(holder: CareViewHolder, position: Int) {
        if (enumList.isArrayOf<Preventions>()) {
            (holder as PreventionsViewHolder).bind(enumList[position] as Preventions)
        } else {
            (holder as SymptomsViewHolder).bind(enumList[position] as Symptoms)
        }
    }

    enum class Preventions {
        WASH_YOUR_HANDS,
        COVER_NOSE_AND_MOUTH,
        AVOID_CLOSE_CONTACT,
        DO_NOT_TOUCH_YOUR_FACE,
        STAY_HOME,
        WEAR_A_MASK_IF_NECESSARY
    }

    enum class Symptoms {
        COUGH,
        FEVER,
        TIREDNESS,
        DIFFICULTY_BREATHING
    }
}