package mobilesdkdemo.rbbn.itswinter.covid;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import mobilesdkdemo.rbbn.itswinter.R;

/**
 * This class is for fragment
 */
public class CovidDetailsFragment extends Fragment {

    private Bundle dataFromActivity;
    private String details;

    private AppCompatActivity parentActivity;

    public CovidDetailsFragment() {
    }

    /**
     * When this fragment starts, this method runs.
     * shows details of Covid data and has a button to close
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        details = dataFromActivity.getString(CovidResultActivity.ITEM_DETAILS );

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_covid_details, container, false);

        //show the message
        TextView message = (TextView)result.findViewById(R.id.Covid_Fragment_Message);
        message.setText(details);

        // get the delete button, and add a click listener:
        Button finishButton = (Button)result.findViewById(R.id.Covid_Fragment_Button);
        finishButton.setOnClickListener( clk -> {

            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();

            if (parentActivity instanceof CovidEmptyActivity ){
                parentActivity.finish();
            }
        });

        return result;
    }

    /**
     * called at the beginning
     * @param context
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }

}