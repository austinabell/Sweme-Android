package austinabell8.sweme.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.austinabell8.cardswipe.SwipeDeck;
import com.austinabell8.cardswipe.layout.SwipeFrameLayout;

import java.util.ArrayList;

import austinabell8.sweme.Meme;
import austinabell8.sweme.R;
import austinabell8.sweme.adapters.SwipeDeckAdapter;


public class DiscoverFragment extends Fragment {

    private View inflatedCustomerLookup;
    private SwipeDeck cardStack;
    private SwipeDeckAdapter adapter;
    private SwipeFrameLayout swipeFrameLayout;
    private int counter;

    private final ArrayList<Meme> mTestData = new ArrayList<>();


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

        counter = 0;
        mTestData.add(new Meme(R.drawable.blue_solid, counter++ + ""));
        mTestData.add(new Meme(R.drawable.green_solid, counter++ + ""));
        mTestData.add(new Meme(R.drawable.yellow_solid, counter++ + ""));



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
        cardStack.setTopImage(R.id.top_image);
        cardStack.setBottomImage(R.id.bottom_image);

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


    private void addNext(){
        if (counter%3==0){
            mTestData.add(new Meme(R.drawable.blue_solid, counter++ + ""));
        }
        else if (counter%3==1){
            mTestData.add(new Meme(R.drawable.green_solid, counter++ + ""));
        }
        else {
            mTestData.add(new Meme(R.drawable.yellow_solid, counter++ + ""));
        }
        adapter.notifyDataSetChanged();
    }

}
