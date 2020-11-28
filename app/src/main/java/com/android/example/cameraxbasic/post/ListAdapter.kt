package com.android.example.cameraxbasic.post

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.example.cameraxbasic.R
import com.android.example.cameraxbasic.databinding.ListItemPostBinding
import com.android.example.cameraxbasic.models.MediaObject
import com.android.example.cameraxbasic.utils.PlayerStateCallback
import com.android.example.cameraxbasic.utils.PlayerViewAdapter.Companion.releaseRecycledPlayers
import com.google.android.exoplayer2.Player
import java.util.ArrayList

class ListAdapter(private val mContext: Context,
    private var modelList: ArrayList<MediaObject>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), PlayerStateCallback {

        private var mItemClickListener: OnItemClickListener? =
                null

        fun updateList(modelList: ArrayList<MediaObject>) {
            this.modelList = modelList
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(
                viewGroup: ViewGroup,
                viewType: Int
        ): VideoPlayerViewHolder {
            val binding: ListItemPostBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context)
                    , R.layout.list_item_post, viewGroup, false)
            return VideoPlayerViewHolder(binding)
        }

        override fun onBindViewHolder(
                holder: RecyclerView.ViewHolder,
                position: Int) {

            //Here you can fill your row view
            if (holder is VideoPlayerViewHolder) {
                val model = getItem(position)
                val genericViewHolder = holder

                // send data to view holder
                genericViewHolder.onBind(model)
            }
        }

        override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
            val position = holder.adapterPosition
            releaseRecycledPlayers(position)
            super.onViewRecycled(holder)
        }

        override fun getItemCount(): Int {
            return modelList.size
        }

        private fun getItem(position: Int): MediaObject {
            return modelList[position]
        }

        fun SetOnItemClickListener(mItemClickListener: OnItemClickListener?) {
            this.mItemClickListener = mItemClickListener
        }

        interface OnItemClickListener {
            fun onItemClick(
                    view: View?,
                    position: Int,
                    model: MediaObject?
            )
        }

        inner class VideoPlayerViewHolder(private val binding: ListItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

            fun onBind(model: MediaObject) {
                // handel on item click
                binding.root.setOnClickListener {
                    mItemClickListener!!.onItemClick(
                            it,
                            adapterPosition,
                            model
                    )
                }

                binding.apply {
                    dataModel = model
                    callback = this@ListAdapter
                    index = adapterPosition
                    executePendingBindings()
                }



            }
        }

        override fun onVideoDurationRetrieved(duration: Long, player: Player) {}

        override fun onVideoBuffering(player: Player) {}

        override fun onStartedPlaying(player: Player) {
            Log.d("playvideo", "staaaart" + player.contentDuration)

        }


        override fun onFinishedPlaying(player: Player) {}
}