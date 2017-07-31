package austinabell8.sweme;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ProfileFragment.OnFragmentInteractionListener mListener;
    private View inflatedCustomerLookup;
    private Button mLogoutButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedCustomerLookup = inflater.inflate(R.layout.fragment_profile, container, false);
        populateCustomerList();

        mLogoutButton = (Button) mLogoutButton.findViewById(R.id.log_out_button);
        mLogoutButton.setOnClickListener(this);

        return inflatedCustomerLookup;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileFragment.OnFragmentInteractionListener) {
            mListener = (ProfileFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_out_button:
                ((MainActivity) getActivity()).logout();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void populateCustomerList(){
//        // Construct the data source
//        ArrayList<Customer> arrayOfCustomers = Customer.getCustomers();
//        // Create the adapter to convert the array to views
//        CustomerListAdapter adapter = new CustomerListAdapter(getActivity(), arrayOfCustomers);
//        // Attach the adapter to a ListView
//        ListView listView = (ListView) inflatedCustomerLookup.findViewById(R.id.lvCustomers);
//        listView.setAdapter(adapter);
    }

}
