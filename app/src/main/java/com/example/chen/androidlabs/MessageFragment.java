package com.example.chen.androidlabs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {

    private View view;
    private TextView message;
    private TextView id;
    private Button btn_delete;
    private ChatDatabaseHelper chatDatabase;

    public MessageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_message_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        message= view.findViewById(R.id.message_here);
        id = view.findViewById(R.id.message_id);
        btn_delete = view.findViewById(R.id.delete_message);

        message.setText(getArguments().getString("Message"));
        id.setText(getArguments().getString("MessageID"));
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments().getBoolean("isTablet")) {
                    chatDatabase = new ChatDatabaseHelper(getActivity());
                    chatDatabase.deleteItem(getArguments().getString("MessageID"));
                    ((ChatWindow)getActivity()).notifyChange();
                    ((ChatWindow)getActivity()).displaySQL();
                    getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("MessageID", getArguments().getString("MessageID"));
                    getActivity().setResult(1, resultIntent);
                    getActivity().finish();
                }
            }
        });
    }

}