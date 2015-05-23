package ar.uba.fi.fiubappMobile.groups;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fiubapp.fiubapp.Popup;
import com.fiubapp.fiubapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fiubapp.fiubapp.dominio.Discussion;

import ar.uba.fi.fiubappMobile.groups.Discussions.DiscussionActivity;
import ar.uba.fi.fiubappMobile.groups.Discussions.DiscussionsAdapter;

public class DiscusionesTab extends Fragment {

    private String urlAPI;
    private DiscussionsAdapter discussionsAdapter;
    private List<Discussion> discussionList = new ArrayList<Discussion>();
    private ListView discussionsListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlAPI = getResources().getString(R.string.urlAPI);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View regularSearchView = inflater.inflate(R.layout.discusiones_grupo_tab, container, false);

        discussionsListView = (ListView) regularSearchView.findViewById(R.id.lstVw_discussions);

        fillDiscussionsList();

        discussionsAdapter = new DiscussionsAdapter(getActivity(), discussionList);
        discussionsListView.setAdapter(discussionsAdapter);

        Button btnCreateDiscussion = (Button)regularSearchView.findViewById(R.id.btnCreateDiscussion);
        btnCreateDiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCreateDiscussion();
            }
        });

        return regularSearchView;

    }

    private void setCreateDiscussion() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View createDiscussionView = layoutInflater.inflate(R.layout.create_discussion, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(createDiscussionView);

        final EditText edtvw_discussion_name = (EditText)createDiscussionView.findViewById(R.id.edtvw_discussion_name);
        final EditText edtvw_first_message = (EditText)createDiscussionView.findViewById(R.id.edtvw_first_message);

        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Iniciar Discusion")
                .setPositiveButton("Iniciar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Discussion discussion = new Discussion();
                                discussion.setDiscussionName(edtvw_discussion_name.getText().toString());
                                discussion.setCreationDate((new Date()).toString());
                                discussion.setCreatorUserName("Testing DiscusionesTab");
                                createDiscussion(discussion, edtvw_first_message.getText().toString());
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void createDiscussion(Discussion discussion, String s) {
    }

    private void fillDiscussionsList() {
        Discussion discussion1 = new Discussion();
        Discussion discussion2 = new Discussion();
        discussion1.setCreationDate("03/05/2015");
        discussion1.setCreatorUserName("86500");
        discussion1.setDiscussionName("Discusion de Prueba numero 1");
        discussion2.setCreationDate("23/02/2015");
        discussion2.setCreatorUserName("86501");
        discussion2.setDiscussionName("Discusion 2");
        discussionList.add(discussion1);
        discussionList.add(discussion2);
    }

    public static DiscusionesTab nuevoGrupo(int idGrupo) {
        DiscusionesTab tab = new DiscusionesTab();
        Bundle args = new Bundle();
        args.putInt("idGrupo",idGrupo);
        tab.setArguments(args);
        return tab;
    }

}
