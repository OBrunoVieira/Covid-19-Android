package com.doubleb.covid19.ui.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.doubleb.covid19.R
import com.doubleb.covid19.ui.adapter.CareListAdapter
import kotlinx.android.synthetic.main.view_holder_prevention.view.*

class PreventionsViewHolder(itemView: View) : CareViewHolder(itemView) {

    fun bind(enum: CareListAdapter.Preventions) {
        when (enum) {
            CareListAdapter.Preventions.WASH_YOUR_HANDS -> {
                itemView.prevention_care_view
                    .illustrationRes(R.drawable.ic_wash_hands)
                    .indicatorColor(R.color.purple_light)
                    .titleRes(R.string.prevention_wash_your_hands_title)
                    .descriptionRes(R.string.prevention_wash_your_hands_description)
                    .build()
            }

            CareListAdapter.Preventions.COVER_NOSE_AND_MOUTH -> {
                itemView.prevention_care_view
                    .illustrationRes(R.drawable.ic_how_to_cough)
                    .indicatorColor(R.color.green_dark)
                    .titleRes(R.string.prevention_cover_nose_and_mouth_title)
                    .descriptionRes(R.string.prevention_cover_nose_and_mouth_description)
                    .build()
            }

            CareListAdapter.Preventions.AVOID_CLOSE_CONTACT -> {
                itemView.prevention_care_view
                    .illustrationRes(R.drawable.ic_avoid_contact)
                    .indicatorColor(R.color.blue_dark)
                    .titleRes(R.string.prevention_avoid_close_contact_title)
                    .descriptionRes(R.string.prevention_avoid_close_contact_description)
                    .build()
            }

            CareListAdapter.Preventions.DO_NOT_TOUCH_YOUR_FACE -> {
                itemView.prevention_care_view
                    .illustrationRes(R.drawable.ic_do_not_touch_the_face)
                    .indicatorColor(R.color.yellow_light)
                    .titleRes(R.string.prevention_do_not_touch_your_face_title)
                    .descriptionRes(R.string.prevention_do_not_touch_your_face_description)
                    .build()
            }

            CareListAdapter.Preventions.STAY_HOME -> {
                itemView.prevention_care_view
                    .illustrationRes(R.drawable.ic_stay_home)
                    .indicatorColor(R.color.purple_light)
                    .titleRes(R.string.prevention_stay_home_title)
                    .descriptionRes(R.string.prevention_stay_home_description)
                    .build()
            }

            CareListAdapter.Preventions.WEAR_A_MASK_IF_NECESSARY -> {
                itemView.prevention_care_view
                    .illustrationRes(R.drawable.ic_mask)
                    .indicatorColor(R.color.green_dark)
                    .titleRes(R.string.prevention_wear_a_mask_if_necessary_title)
                    .descriptionRes(R.string.prevention_wear_a_mask_if_necessary_description)
                    .build()
            }
        }
    }
}