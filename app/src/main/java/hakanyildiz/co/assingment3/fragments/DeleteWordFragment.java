package hakanyildiz.co.assingment3.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import hakanyildiz.co.assingment3.MyClasses.DatabaseHelper;
import hakanyildiz.co.assingment3.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeleteWordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeleteWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteWordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayAdapter adapter;
    ListView listView;
    DatabaseHelper databaseHelper;
    LinearLayout llInfo, llListView;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteWordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteWordFragment newInstance(String param1, String param2) {
        DeleteWordFragment fragment = new DeleteWordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DeleteWordFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_delete_word, container, false);

        databaseHelper = new DatabaseHelper(getActivity());
        setup(view, getActivity());

        return view;
    }

    private void setup(View view, Context context) {
        llInfo = (LinearLayout) view.findViewById(R.id.llInfo);
        llListView = (LinearLayout) view.findViewById(R.id.llListView);
        listView = (ListView) view.findViewById(R.id.listViewDeleteFragment);
        listView.setAdapter(adapter);

        adapter = databaseHelper.getAllDictionaries(context);

        if(adapter != null) //kayitli veri var demektir.
        {
            llListView.setVisibility(View.VISIBLE);
            llInfo.setVisibility(View.INVISIBLE);
            listView.setAdapter(adapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                    String selectedFromList = (String) (listView.getItemAtPosition(myItemInt));
                    Toast.makeText(getActivity(), "CLicked For Delete : " + selectedFromList, Toast.LENGTH_LONG).show();
                    Log.d("HAKKE", "DeleteWordFrag => selectedItemName " + selectedFromList);

                    String[] arr = selectedFromList.replaceAll("\\s+"," ").split(" ");
                    final int currentID = Integer.parseInt(arr[0]);
                    String currentTUrk = arr[1];
                    String currentEnglish = arr[2];

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.delete_dialog);
                    dialog.setTitle("Do you want to delete it?");

                    TextView tvDeletedName = (TextView) dialog.findViewById(R.id.tv_deletingItemName);
                     Button btnDelete = (Button) dialog.findViewById(R.id.btnDelete_dialog);
                    Button btnClose = (Button) dialog.findViewById(R.id.btnClose_dialog);
                    tvDeletedName.setText("You choose: " + currentTUrk);

                    btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean valid = databaseHelper.deleteDictionary(currentID);
                            if(valid)
                            {
                                Toast.makeText(getActivity(),"Deleting is completed! :)",Toast.LENGTH_SHORT).show();
                                adapter = databaseHelper.getAllDictionaries(getActivity());
                                listView.setAdapter(adapter);
                                dialog.dismiss();
                            }
                        }
                    });
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
        else //database de veri olmadıgını gösteren bir editText göster.
        {
            llInfo.setVisibility(View.VISIBLE);
            llListView.setVisibility(View.INVISIBLE);
        }


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
