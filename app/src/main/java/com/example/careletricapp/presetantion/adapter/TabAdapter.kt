package com.example.careletricapp.presetantion.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.careletricapp.presetantion.CarFragment
import com.example.careletricapp.presetantion.FavoritosFragment

class TabAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {


    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CarFragment()
            1 -> FavoritosFragment()
            else -> CarFragment()
        }
    }

    override fun getItemCount(): Int {
            return 2
    }

}