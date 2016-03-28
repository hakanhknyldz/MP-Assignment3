package hakanyildiz.co.assingment3.fragments;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hakanyildiz.co.assingment3.MyClasses.DatabaseHelper;
import hakanyildiz.co.assingment3.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateWordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateWordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayAdapter adapter;
    DatabaseHelper databaseHelper;
    LinearLayout llInfo, llListView;
    int CurrentID;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateWordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateWordFragment newInstance(String param1, String param2) {
        UpdateWordFragment fragment = new UpdateWordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UpdateWordFragment() {
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
        View view = inflater.inflate(R.layout.fragment_update_word, container, false);
        databaseHelper = new DatabaseHelper(getActivity());
        setup(view, getActivity());


        return view;
    }

    private void setup(final View view, Context context) {

        final ListView listView = (ListView) view.findViewById(R.id.listViewUpdateFragment);
        listView.setAdapter(adapter);

        adapter = databaseHelper.getAllDictionaries(context);

        if (adapter != null) //kayitli veri var demektir.
        {

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                    String selectedFromList = (String) (listView.getItemAtPosition(myItemInt));
                    Toast.makeText(getActivity(), "CLicked : " + selectedFromList, Toast.LENGTH_LONG).show();
                    Log.d("HAKKE", "UpdateWordFrag => selectedItemName " + selectedFromList);

                    setupDialog(selectedFromList, view, listView);
                }
            });


        } else //database de veri olmadıgını gösteren bir editText göster.
        {

        }


    }

    private void setupDialog(String selectedFromList, View view, final ListView listView) {
        String[] arr = selectedFromList.replaceAll("\\s+", " ").split(" ");
        CurrentID = Integer.parseInt(arr[0]);
        String oldTurk = arr[1];
        String oldEnglish = arr[2];

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.update_dialog);
        dialog.setTitle("Do you want to update it?");

        Button btnCLose = (Button) dialog.findViewById(R.id.btnClose_updatedialog);
        btnCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate_updatedialog);
        final TextView tvStatus = (TextView) dialog.findViewById(R.id.tv_updatedialog_staus);
        final EditText etNewTurk = (EditText) dialog.findViewById(R.id.etNewTurkishWord);
        final EditText etNewEnglish = (EditText) dialog.findViewById(R.id.etNewEnglishWord);


        tvStatus.setText("Current Turkish Word: " + oldTurk + " , English Meaning: " + oldEnglish);

        String newTurk = "";
        String newEnglish = "";

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("HAKKE","updatefragment => newTurk:" + etNewTurk.getText().toString() + " , newEnglish:" + etNewEnglish.getText().toString());

                if (etNewTurk.getText().toString().length() == 0 || etNewEnglish.getText().toString().length() == 0) {
                    tvStatus.append(" PLEASE, FILL THE TEXT ");
                    //do nothing!
                } else {
                    boolean valid = databaseHelper.updateDictionary(etNewTurk.getText().toString().toLowerCase(), etNewEnglish.getText().toString().toLowerCase(), CurrentID);
                    if (valid) {
                        Toast.makeText(getActivity(), "Update is successfully", Toast.LENGTH_SHORT).show();
                        adapter = databaseHelper.getAllDictionaries(getActivity());
                        listView.setAdapter(adapter);
                        dialog.dismiss();
                    }
                }

            }
        });



        dialog.show();

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
