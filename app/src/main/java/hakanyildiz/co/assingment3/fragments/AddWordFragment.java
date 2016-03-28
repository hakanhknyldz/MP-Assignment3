package hakanyildiz.co.assingment3.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import hakanyildiz.co.assingment3.MyClasses.DatabaseHelper;
import hakanyildiz.co.assingment3.MyClasses.Dictionary;
import hakanyildiz.co.assingment3.MyClasses.User;
import hakanyildiz.co.assingment3.R;

public class AddWordFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    Button btnSave;
    EditText etTurk, etEnglish;
    Context context;
    ArrayAdapter arrayAdapter;
    private OnFragmentInteractionListener mListener;
    DatabaseHelper databaseHelper;
    ListView listView;
    TextView tvStatusListView;

    public static AddWordFragment newInstance(String param1, String param2) {
        AddWordFragment fragment = new AddWordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddWordFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_word, container, false);
        context = getActivity();
        databaseHelper = new DatabaseHelper(context);
        setupListView(view,context);
        setup(view);

        return view;
    }

    private void setupListView(View view,Context context) {
        tvStatusListView = (TextView) view.findViewById(R.id.tvStatusListView);
        listView = (ListView) view.findViewById(R.id.listViewAddFragment);
        arrayAdapter = databaseHelper.getAllDictionaries(context);

        if(arrayAdapter != null) //kayitli veri var demektir.
        {
            tvStatusListView.setVisibility(View.INVISIBLE);

            listView.setAdapter(arrayAdapter);
        }
        else //database de veri olmadıgını gösteren bir editText göster.
        {
            tvStatusListView.setVisibility(View.VISIBLE);
        }

    }

    private void setup(View view) {
        btnSave = (Button) view.findViewById(R.id.btnSaveInAddFragment);
        etTurk = (EditText) view.findViewById(R.id.etTurkishWord);
        etEnglish = (EditText) view.findViewById(R.id.etEnglishWord);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    //method here.

                    Dictionary myDictionary = new Dictionary();
                    myDictionary.setTurkishWord(etTurk.getText().toString());
                    myDictionary.setEnglishWord(etEnglish.getText().toString());

                    boolean result = databaseHelper.insertDictionary(myDictionary);

                    if(result)
                    {
                        Toast.makeText(context,"Word added! :)",Toast.LENGTH_SHORT).show();
                        arrayAdapter = databaseHelper.getAllDictionaries(context);
                        listView.setAdapter(arrayAdapter);
                    }
                    else
                    {
                        Toast.makeText(context,"Word not adding ://", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }

    private boolean validate() {

        boolean valid = true;
        String turkceKelime = etTurk.getText().toString();
        String inglizceKelime = etEnglish.getText().toString();

        if(turkceKelime.isEmpty())
        {
            etTurk.setError("Please, enter the Turkish word");
            valid = false;
        }
        else
        {
            etTurk.setError(null);
        }

        if(inglizceKelime.isEmpty())
        {
            etEnglish.setError("Pleasei enter the English meaning of Turkish Word");
            valid = false;
        }
        else
        {
            etEnglish.setError(null);
        }

        return  valid;
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
