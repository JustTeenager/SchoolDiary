package com.example.schooldiary.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.schooldiary.R;
import com.example.schooldiary.databinding.FragmentEditDiaryBinding;

public class EditDiaryFragment extends Fragment {

    private FragmentEditDiaryBinding binding;

    public static EditDiaryFragment newInstance(String nameSubject,String date) {
        Bundle args = new Bundle();
        EditDiaryFragment fragment = new EditDiaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_diary,container,false);
        binding.viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager(),FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (position<2)
                return NotesFragment.newInstance(position);
                else{
                    return RatingFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0){
                    return getActivity().getString(R.string.homework);
                }
                else if(position == 1){
                    return getActivity().getString(R.string.note);
                }
                else if(position == 2){
                    return getActivity().getString(R.string.rating);
                }
                return super.getPageTitle(position);
            }

        });

        return binding.getRoot();
    }
}
