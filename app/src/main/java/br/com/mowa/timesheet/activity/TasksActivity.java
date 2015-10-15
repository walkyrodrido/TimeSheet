package br.com.mowa.timesheet.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.mowa.timesheet.adapter.TasksRecyclerviewAdapter;
import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseTask;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

public class TasksActivity extends BaseActivity {
    private ListView listView;
    private ParseTask parseTask;
    private List<TaskModel> listTaskModel;
    private CallJsonNetwork callJson;
//    private SwipeRefreshLayout swipeLayout;
    private LinearLayoutManager layoutManager;
    private ProgressDialog progress;
    private RecyclerView recycler;
    private UserModel user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        this.progress = createProgressDialog("Loading", "carregando lista de tarefas", true, true);
        this.progress.show();
        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_registros_drawer_layout);
        NavigationDrawerFragment navDraFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_registros_fragment_navigation_drawer_container);
        navDraFragment.setUp(drawerLayout, createToolbar(R.id.activity_registros_toolbar));

//        this.swipeLayout = (SwipeRefreshLayout) findViewById(R.id.activity_registros_swipe_to_refresh);
//        this.swipeLayout.setOnRefreshListener(OnRefreshListener());
//        this.swipeLayout.setColorSchemeResources(R.color.green, R.color.red, R.color.blue);


//        listView = (ListView) findViewById(R.id.activity_registros_list_view);
        parseTask = new ParseTask();
        callJson = new CallJsonNetwork();
        loadRegistros();

    }

    /**
     * Chamada GET em todas as tarefas do usuário logado
     * adapta a lista em um recyclerView
     */
    private void loadRegistros() {
        callJson.callJsonObjectGet(VolleySingleton.URL_GET_TASK_USER_ID + user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listTaskModel = parseTask.jsonObjectToTaskEntity(response);
                    recycler = (RecyclerView) findViewById(R.id.rv);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recycler.setLayoutManager(layoutManager);
                    DefaultItemAnimator animator = new DefaultItemAnimator();
                    animator.setMoveDuration(1000);
                    animator.setChangeDuration(1000);
                    recycler.setItemAnimator(animator);
                    recycler.setHasFixedSize(true);
                    TasksRecyclerviewAdapter adapter = new TasksRecyclerviewAdapter(listTaskModel, interfaceOnClick());
                    recycler.setAdapter(adapter);
                    registerForContextMenu(recycler);
                    progress.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("walkyTeste", "Deu ruim");
            }
        });
    }


    /**
     * Caso um item do Recycler seja clicado, esse metodo da interface será chamado;
     * @return
     */
    private TasksRecyclerviewAdapter.ClickRecycler interfaceOnClick() {
        return new TasksRecyclerviewAdapter.ClickRecycler() {
            @Override
            public void onClickIntemRecycler(int position) {
                TaskModel r = listTaskModel.get(position);
                toast(r.getName());
            }
        };
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_registros_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }


    /**
     * Interface OnRefreshListener do SwipeRefreshLayout
     * @return
     */
//    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
//        return new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (IsConnectionNetworkAvailable.isNetworkAvailable(getContext())) {
//                    loadRegistros();
//                } else {
//                    toast("erro - verifique sua conexão");
//                    stopRefresh();
//                }
//
//            }
//        };
//    }

    /**
     * Metodo para cancelar o swipeRefresh
     */
//    private void stopRefresh() {
//        if (swipeLayout.isRefreshing()) {
//            swipeLayout.setRefreshing(false);
//        }
//    }
}