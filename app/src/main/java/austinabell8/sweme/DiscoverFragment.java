package austinabell8.sweme;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.daprlabs.aaron.swipedeck.layouts.SwipeFrameLayout;

import java.util.ArrayList;


public class DiscoverFragment extends Fragment implements View.OnClickListener {

    private DiscoverFragment.OnFragmentInteractionListener mListener;
    private View inflatedCustomerLookup;
    private SwipeDeck cardStack;
    private SwipeDeckAdapter adapter;
    private SwipeFrameLayout swipeFrameLayout;

    private final ArrayList<String> mTestData = new ArrayList<>();


    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedCustomerLookup = inflater.inflate(R.layout.fragment_discover, container, false);

        cardStack = (SwipeDeck) inflatedCustomerLookup.findViewById(R.id.swipe_deck);
        swipeFrameLayout = (SwipeFrameLayout) inflatedCustomerLookup.findViewById(R.id.swipeLayout);

        mTestData.add("0");
        mTestData.add("1");
        mTestData.add("2");
        mTestData.add("3");
        mTestData.add("4");

        adapter = new SwipeDeckAdapter(mTestData, getActivity());
        if(cardStack != null){
            cardStack.setAdapter(adapter);
        }
        cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(long stableId) {
                addNext();
            }

            @Override
            public void cardSwipedRight(long stableId) {
                addNext();
            }

            @Override
            public void cardSwipedTop(long itemId) {
                addNext();
            }

            @Override
            public void cardSwipedBottom(long itemId) {
                addNext();
            }

            @Override
            public boolean isDragEnabled(long itemId) {
                return true;
            }

        });

        cardStack.setLeftImage(R.id.left_image);
        cardStack.setRightImage(R.id.right_image);

        //example of buttons triggering events on the deck
        ImageButton cancelButton = (ImageButton) inflatedCustomerLookup.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardLeft(400);
            }
        });
        ImageButton likeButton = (ImageButton) inflatedCustomerLookup.findViewById(R.id.like_button);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardRight(400);
            }
        });
        ImageButton saveButton = (ImageButton) inflatedCustomerLookup.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardTop(600);
            }
        });
        ImageButton nextButton = (ImageButton) inflatedCustomerLookup.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardBottom(600);
            }
        });

        return inflatedCustomerLookup;
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        swipeFrameLayout.setBackgroundResource(0);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        swipeFrameLayout.setBackgroundResource(R.drawable.primary_fade);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DiscoverFragment.OnFragmentInteractionListener) {
            mListener = (DiscoverFragment.OnFragmentInteractionListener) context;
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

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void addNext(){
        mTestData.add("a sample string.");
        adapter.notifyDataSetChanged();
    }

    public void addBackgroundFade (Boolean b) {
        if (b==true){
            swipeFrameLayout.setBackgroundResource(R.drawable.primary_fade);
        }
        else {
            swipeFrameLayout.setBackgroundResource(0);
        }
    }
}
