package com.powerlifting.calculator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.powerlifting.calculator.R;
import com.powerlifting.calculator.adapters.NormsTableAdapter;

public class NormsFragment extends Fragment {

    private TableFixHeaders tableFixHeaders;

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            tableFixHeaders.setAdapter(new NormsTableAdapter(getActivity(), position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.norms_fragment, null);

        String[] FEDERATIONS = getResources().getStringArray(R.array.federations_names);
        Spinner normsSpinner = (Spinner) view.findViewById(R.id.norms_spinner);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, FEDERATIONS);
        adapter.setDropDownViewResource(R.layout.spiner_item);
        normsSpinner.setAdapter(adapter);
        normsSpinner.setOnItemSelectedListener(onItemSelectedListener);

        tableFixHeaders = (TableFixHeaders) view.findViewById(R.id.table);
        tableFixHeaders.setAdapter(new NormsTableAdapter(getActivity(), 0));


        return view;
    }
}
