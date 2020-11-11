package mobilesdkdemo.rbbn.itswinter.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mobilesdkdemo.rbbn.itswinter.AudioHomeActivity;
import mobilesdkdemo.rbbn.itswinter.CovidHomeActivity;
import mobilesdkdemo.rbbn.itswinter.EventHomeActivity;
import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.RecipeHomeActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView ivRecipe, ivCovid, ivAudio, ivEvent;
    View v;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_home, container, false);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ivRecipe=v.findViewById(R.id.ivRecipe);
        ivCovid=v.findViewById(R.id.ivCovid);
        ivAudio=v.findViewById(R.id.ivAudio);
        ivEvent=v.findViewById(R.id.ivEvent);

        ivRecipe.setOnClickListener(v->{
            startActivity(new Intent(getActivity(), RecipeHomeActivity.class));
        });

        ivCovid.setOnClickListener(v->{
            startActivity(new Intent(getActivity(), CovidHomeActivity.class));
        });

        ivAudio.setOnClickListener(v->{
            startActivity(new Intent(getActivity(), AudioHomeActivity.class));
        });
        ivEvent.setOnClickListener(v->{
            startActivity(new Intent(getActivity(), EventHomeActivity.class));
        });
    }
}