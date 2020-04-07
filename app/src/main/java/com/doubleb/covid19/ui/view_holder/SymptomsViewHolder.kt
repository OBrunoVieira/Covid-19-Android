package com.doubleb.covid19.ui.view_holder

import android.view.View
import com.doubleb.covid19.R
import com.doubleb.covid19.ui.adapter.CareListAdapter
import kotlinx.android.synthetic.main.view_holder_symptoms.view.*

class SymptomsViewHolder(itemView: View) : CareViewHolder(itemView) {

    fun bind(enum: CareListAdapter.Symptoms) {
        when (enum) {
            CareListAdapter.Symptoms.COUGH -> {
                itemView.symptoms_care_view
                    .illustrationRes(R.drawable.ic_cough)
                    .indicatorColor(R.color.purple_light)
                    .titleRes(R.string.symptoms_cough_title)
                    .descriptionRes(R.string.symptoms_cough_description)
                    .build()
            }

            CareListAdapter.Symptoms.FEVER -> {
                itemView.symptoms_care_view
                    .illustrationRes(R.drawable.ic_fever)
                    .indicatorColor(R.color.green_dark)
                    .titleRes(R.string.symptoms_fever_title)
                    .descriptionRes(R.string.symptoms_fever_description)
                    .build()
            }

            CareListAdapter.Symptoms.TIREDNESS -> {
                itemView.symptoms_care_view
                    .illustrationRes(R.drawable.ic_tiredness)
                    .indicatorColor(R.color.blue_dark)
                    .titleRes(R.string.symptoms_tiredness_title)
                    .descriptionRes(R.string.symptoms_tiredness_description)
                    .build()
            }

            CareListAdapter.Symptoms.DIFFICULTY_BREATHING -> {
                itemView.symptoms_care_view
                    .illustrationRes(R.drawable.ic_difficulty_breathing)
                    .indicatorColor(R.color.yellow_light)
                    .titleRes(R.string.symptoms_difficulty_breathing_title)
                    .descriptionRes(R.string.symptoms_difficulty_breathing_description)
                    .build()
            }
        }
    }

}