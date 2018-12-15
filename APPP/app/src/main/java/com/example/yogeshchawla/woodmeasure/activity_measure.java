package com.example.yogeshchawla.woodmeasure;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Yogesh Chawla on 6/21/2018.
 */

public class activity_measure extends Fragment {
    TableLayout tbl;
    TextView lblsrno,total;
    Button button,button1,btnclear,btnone,btntwo,btnthree,btnfour,btnfive,btnsix,btnseven,btneight,btnnine,btnpoint,btnzero,btntab;
    Spinner spnparty,spntype;
    ArrayList<TablePojo> pj;
    String ip;
    int count;
    TableOperations to;
    JSONObject mainjo;
    JSONArray ja;
    AlertDialog.Builder builder;
    String ch;
    boolean saveflag=false;


    ScrollView sv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments()!=null) {
            ch = getArguments().getString("ch");
            if (ch != null) {
                Log.d("57frag", ch);
            }
        }

        return inflater.inflate(R.layout.fragment_measure2, container, false);
    }

    private void putKeyEffect(String key)
    {
        for(int i=0;i<tbl.getChildCount();i++)
        {
            TableRow tr=(TableRow)tbl.getChildAt(i);
            View view=null;
            for(int j=0;j<tr.getChildCount();j++)
            {
                view=tr.getChildAt(j);
                if(view instanceof  EditText)
                {
                    EditText et=(EditText)view;
                    if(et.hasFocus())
                    {     et.setBackgroundColor( getActivity().getResources().getColor(R.color.c));
                        int startSelection=et.getSelectionStart();
                        int endSelection=et.getSelectionEnd();

                        String selectedText = et.getText().toString().substring(startSelection, endSelection);
                        if(selectedText.isEmpty()) {
                            et.setText(et.getText()+key);
                        }
                        else
                        {
                            et.setText(key);
                        }
                        int l = et.getText().toString().length();
                        et.setSelection(l);

                    }
                    else
                    {
                        et.setBackgroundColor( getActivity().getResources().getColor(R.color.o));
                    }
                }
            }
        }
    }
    @Override
    public void onStop()
    {   super.onStop();
        Log.d("onstop","111");
        if(!saveflag) {
            count = tbl.getChildCount();
            SQLiteDatabase mydatabase = activity_measure.this.getActivity().openOrCreateDatabase("mydb", MODE_PRIVATE, null);
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS record(srno VARCHAR,length VARCHAR,width VARCHAR,thick VARCHAR,cft VARCHAR);");
            mydatabase.execSQL("delete from record;");
            for (int i = 1; i < count - 1; i++) {

                TableRow ttrc = (TableRow) tbl.getChildAt(i);
                TextView tvv1, tvv2;
                EditText eet1, eet2, eet3;
                tvv1 = (TextView) ttrc.findViewById(R.id.tvsrno1);
                tvv2 = (TextView) ttrc.findViewById(R.id.tvcfeet1);
                eet1 = (EditText) ttrc.findViewById(R.id.tvlength1);
                eet2 = (EditText) ttrc.findViewById(R.id.tvthick1);
                eet3 = (EditText) ttrc.findViewById(R.id.tvheight1);
                String srno = tvv1.getText().toString();
                String cft = tvv2.getText().toString();
                String len = eet1.getText().toString();
                String thick = eet2.getText().toString();
                String width = eet3.getText().toString();
                try {
                    String sql = "INSERT INTO record VALUES('" + srno + "','" + len + "','" + width + "','" + thick + "','" + cft + "');";
                    Log.d("hello", sql);
                    mydatabase.execSQL(sql);
                    Log.d("hello", "success");

                } catch (Exception e) {
                    Log.e("msg", e.getMessage());
                }
            }

            //
            // Toast.makeText(activity_measure.this.getActivity(), "Data Saved Locally", Toast.LENGTH_LONG).show();



        }
       Intent in = new Intent(activity_measure.this.getActivity(), LoginActivity.class);

        activity_measure.this.getActivity().startActivity(in);
        saveflag=false;



    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Measure");
        tbl=(TableLayout)view.findViewById(R.id.measuretbl);
        btnclear=(Button)view.findViewById(R.id.btnbackspace);
        btnone=(Button)view.findViewById(R.id.btnone);
        btntwo=(Button)view.findViewById(R.id.btntwo);
        btnthree=(Button)view.findViewById(R.id.btnthree);
        btnfour=(Button)view.findViewById(R.id.btnfour);
        btnfive=(Button)view.findViewById(R.id.btnfive);
        btnsix=(Button)view.findViewById(R.id.btnsix);
        btnseven=(Button)view.findViewById(R.id.btnseven);
        btneight=(Button)view.findViewById(R.id.btneight);
        btnnine=(Button)view.findViewById(R.id.btnnine);
        btnpoint=(Button)view.findViewById(R.id.btnpoint);
        btnzero=(Button)view.findViewById(R.id.btnzero);
        btntab=(Button)view.findViewById(R.id.btntab);
        sv=(ScrollView) view.findViewById(R.id.scid);
        builder = new AlertDialog.Builder(getActivity());
        spnparty = (Spinner)view.findViewById(R.id.spnparty);
        spntype = (Spinner)view.findViewById(R.id.spntype);
        lblsrno = (TextView)view.findViewById(R.id.lblSrno);
        total = (TextView)view.findViewById(R.id.tvtotal);
        button1 = (Button)view.findViewById(R.id.button1);
        SharedPreferences sp = getActivity().getSharedPreferences("ipfile", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("ip","http://192.168.1.2:8080/WoodApplication");
//        editor.commit();
        button = (Button)view.findViewById(R.id.button);

        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<tbl.getChildCount();i++)
                {
                    TableRow tr=(TableRow)tbl.getChildAt(i);
                    View view=null;
                    for(int j=0;j<tr.getChildCount();j++)
                    {
                        view=tr.getChildAt(j);
                        if(view instanceof  EditText)
                        {
                            EditText et=(EditText)view;
                            if(et.hasFocus())
                            {
                                String text=et.getText().toString();
                                if(text.length()>1)
                                {
                                    et.setText(text.substring(0,text.length()-1));
                                }
                                else
                                {
                                    et.setText("");
                                }

                            }

                        }
                    }
                }

            }
        });
        btnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putKeyEffect("1");

            }
        });
        btntwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putKeyEffect("2");

            }
        });
        btnthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putKeyEffect("3");

            }
        });
        btnfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putKeyEffect("4");

            }
        });
        btnfive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                putKeyEffect("5");


            }
        });
        btnsix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putKeyEffect("6");

            }
        });
        btnseven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putKeyEffect("7");

            }
        });
        btneight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putKeyEffect("8");

            }
        });
        btnnine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putKeyEffect("9");

            }
        });
        btnpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putKeyEffect(".");

            }
        });
        btnzero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putKeyEffect("0");

            }
        });
        btntab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int r=0,c=0;
                boolean flag=false;
                int nor=tbl.getChildCount();
                for(int i=0;i<tbl.getChildCount();i++)
                {
                    TableRow tr=(TableRow)tbl.getChildAt(i);
                    View view=null;
                    for(int j=0;j<tr.getChildCount();j++)
                    {
                        view=tr.getChildAt(j);
                        if(view instanceof  EditText)
                        {
                            View et=view;
                            if(et.hasFocus())
                            {
                                r=i;
                                c=j;
                                flag=true;
                               //
                                //
                                // et.setBackgroundColor( getActivity().getResources().getColor(R.color.o));

                            }
                            et.setBackgroundColor( getActivity().getResources().getColor(R.color.o));
                        }
                    }
                }
                if(flag)
                {int posc=0,posr=0;
                        Log.d("269-",nor+"  "+r);
                    if(r<=tbl.getChildCount()) {
                        if(c<=4)
                        {
                            if(c==3)
                            {
                                Log.d("added",r+"  "+nor+"" +"  "+c);

                                posr=r;
                                posc=c+1;
                                if(r==nor-1) {
                                    TableOperations too = new TableOperations();
                                    too.addRow(tbl, r + 1, getActivity(), view);

                                }
                                View et = ((TableRow) tbl.getChildAt(r + 1)).getChildAt(1);
                                et.setBackgroundColor(getActivity().getResources().getColor(R.color.c));
                                et.requestFocus();


                            }

                            if(c<3)
                            {
                                posr=r;
                                posc=c+1;
                                View et=((TableRow)tbl.getChildAt(posr)).getChildAt(posc);
                                et.setBackgroundColor(getActivity().getResources().getColor(R.color.c));
                                et.requestFocus();
                            }

                        }

                    }
                    else
                    {
                        if(c==4)
                        {
                            posc=1;
                            posr=r+1;
                            TableOperations too = new TableOperations();
                            too.addRow(tbl,posr,getActivity(),view);
                        }
                        else
                        {
                            posc=c+1;
                            posr=r;
                        }

                        EditText et=(EditText)((TableRow)tbl.getChildAt(posr)).getChildAt(posc);
                        et.setBackgroundColor(getActivity().getResources().getColor(R.color.c));
                        et.requestFocus();
                    }
                }

            }
        });
        ip = sp.getString("ip","http://192.168.1.46:8084/WoodApplication");

      //  Toast.makeText(getActivity(),ip, Toast.LENGTH_SHORT).show();
        pj = new ArrayList();
        to = new TableOperations();
        final MyTask mt = new MyTask();
        try {
            mt.execute();


        }
        catch (Exception e)
        {
        }
        try {
            if(ch!=null) {
                Log.d("406",ch);
                    if (ch.equals("OLD")) {
                        Log.d("407","old");
                    SQLiteDatabase mydatabase = activity_measure.this.getActivity().openOrCreateDatabase("mydb", MODE_PRIVATE, null);
                    mydatabase.execSQL("CREATE TABLE IF NOT EXISTS record(srno VARCHAR,length VARCHAR,width VARCHAR,thick VARCHAR,cft VARCHAR);");
                    Cursor cursor = mydatabase.rawQuery("select * from record ", null);
                    ArrayList<TablePojo> listpojo = new ArrayList<TablePojo>();
                    int srno=0;
                    while (cursor.moveToNext()) {
                        Log.d("414","loop");
                         srno = Integer.parseInt(cursor.getString(0));
                        double len = Double.parseDouble(cursor.getString(1));
                        double width = Double.parseDouble(cursor.getString(2));
                        double thick = Double.parseDouble(cursor.getString(3));
                        double cft = Double.parseDouble(cursor.getString(4));
                        listpojo.add(new TablePojo(srno, len, width, thick, cft));

                    }
                    double s = 0.0;

                    for (int i = 0; i < listpojo.size(); i++) {
                        to.addRow(tbl, 1, getActivity(), view, listpojo.get(i));
                        s = s + listpojo.get(i).getCubicfeet();
                    }
                    String stotal = String.format("%.3f",s);
                    total.setText(stotal + "");
                    to.addRow(tbl, srno+1, getActivity(), view);
                        sv.fullScroll(View.FOCUS_DOWN);
                } else {
                    to.addRow(tbl, 1, getActivity(), view);

                }

            }
            else
            {
                to.addRow(tbl, 1, getActivity(), view);
            }

        }
        catch(Exception e)
        {
            Toast.makeText(getActivity(),"Activity Measure 79 : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                   builder.setMessage("Are You Sure").setTitle("Clear")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    for (int j = 1; j < tbl.getChildCount(); j++) {
                                        View view11 = tbl.getChildAt(j);
                                        tbl.removeView(view11);
                                        j--;
                                    }
                                    Fragment fragment = new activity_measure();
                                    FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                                    ft.replace(R.id.content, fragment);
                                    ft.addToBackStack(null);
                                    ft.commit();
                                    to.addRow(tbl, 1, getActivity(), view);
                                    to.setTotal(0.0);
                                    total.setText(to.getTotal() + "");
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Clear");
                    alert.show();
                }
                catch(Exception e)
                {
                    Toast.makeText(getActivity(),"Activity Measure 111 : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }

        });

        button.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater=activity_measure.this.getActivity().getLayoutInflater();
                View alertLayout =inflater.inflate(R.layout.layout_custom_dialog,null);
                //final AlertDialog dialog=builder.show();

                final TextView submit= (TextView) alertLayout.findViewById(R.id.tvsub);
                final TextView ays= (TextView) alertLayout.findViewById(R.id.tvays);
                final Button savebtn=(Button)alertLayout.findViewById(R.id.savebtn);
                final Button yesbtn=(Button)alertLayout.findViewById(R.id.yesbtn);
                final Button nobtn=(Button)alertLayout.findViewById(R.id.nobtn);
                count = tbl.getChildCount();
                try {
                    if (count == 2) {
                        Toast.makeText(getActivity(), "Add Some Measurements", Toast.LENGTH_SHORT).show();
                    } else {
                 //       builder.setMessage("Are You Sure").setTitle("Submit")
                        builder.setView(alertLayout);
                        builder.setCancelable(false);
                        final AlertDialog alert = builder.create();
                        //alert.setTitle("Submit");
                        alert.show();
                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("hello","local");
                                SQLiteDatabase mydatabase = activity_measure.this.getActivity().openOrCreateDatabase("mydb",MODE_PRIVATE,null);
                                mydatabase.execSQL("CREATE TABLE IF NOT EXISTS record(srno VARCHAR,length VARCHAR,width VARCHAR,thick VARCHAR,cft VARCHAR);");
                                mydatabase.execSQL("delete from record;");
                                for (int i = 1; i < count-1; i++) {

                                    TableRow ttrc = (TableRow) tbl.getChildAt(i);
                                    TextView tvv1, tvv2;
                                    EditText eet1, eet2, eet3;
                                    tvv1 = (TextView) ttrc.findViewById(R.id.tvsrno1);
                                    tvv2 = (TextView) ttrc.findViewById(R.id.tvcfeet1);
                                    eet1 = (EditText) ttrc.findViewById(R.id.tvlength1);
                                    eet2 = (EditText) ttrc.findViewById(R.id.tvthick1);
                                    eet3 = (EditText) ttrc.findViewById(R.id.tvheight1);
                                    String srno=tvv1.getText().toString();
                                    String cft=tvv2.getText().toString();
                                    String len=eet1.getText().toString();
                                    String thick=eet2.getText().toString();
                                    String width=eet3.getText().toString();
                                    try {
                                        String sql="INSERT INTO record VALUES('" + srno + "','" + len + "','" + width + "','" + thick + "','" + cft + "');";
                                        Log.d("hello", sql);
                                        mydatabase.execSQL(sql);
                                        Log.d("hello","success");

                                    }
                                    catch(Exception e)
                                    {
                                        Log.e("msg",e.getMessage());
                                    }
                                }
                                // Intent in=new Intent(activity_measure.this.getActivity(),LoginActivity.class);
                                Toast.makeText(activity_measure.this.getActivity(),"Data Saved Locally",Toast.LENGTH_LONG).show();
                                alert.dismiss();
                            }
                        });

                        yesbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mainjo = new JSONObject();
                                ja = new JSONArray();
                                saveflag=true;
                                for (int i = 1; i < count - 1; i++) {
                                    JSONObject jo = new JSONObject();
                                    TableRow ttrc = (TableRow) tbl.getChildAt(i);
                                    TextView tvv1, tvv2;
                                    EditText eet1, eet2, eet3;
                                    tvv1 = (TextView) ttrc.findViewById(R.id.tvsrno1);
                                    tvv2 = (TextView) ttrc.findViewById(R.id.tvcfeet1);
                                    eet1 = (EditText) ttrc.findViewById(R.id.tvlength1);
                                    eet2 = (EditText) ttrc.findViewById(R.id.tvthick1);
                                    eet3 = (EditText) ttrc.findViewById(R.id.tvheight1);
                                    try {
                                        jo.put("srno", tvv1.getText().toString());
                                        jo.put("cfeet", tvv2.getText().toString());
                                        jo.put("length", eet1.getText().toString());
                                        jo.put("thick", eet2.getText().toString());
                                        jo.put("height", eet3.getText().toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                    }

                                    ja.put(jo);
                                }
                                try {
                                    mainjo.put("party", spnparty.getSelectedItem().toString());
                                    mainjo.put("srn", lblsrno.getText().toString());
                                    mainjo.put("type", spntype.getSelectedItem().toString());
                                    mainjo.put("total", total.getText().toString());
                                    mainjo.put("array", ja);
                                    Log.e("168",mainjo.toString());
                                }
                                catch(NullPointerException nfe)
                                {
                                    Toast.makeText(getActivity(),"No Server Connectivity",Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e) {
                                    Log.e("169",e.getLocalizedMessage()+"");

                                }

                                MyTask1 nm = new MyTask1();
                                try {
                                    nm.execute();

                                }
                                catch(Exception e)
                                {

                                }
                                alert.dismiss();

                            }
                        });
                        nobtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("clikc","click");
                               alert.dismiss();

                            }
                        });

                     /*           builder.setCancelable(false)
                                        .setNeutralButton("save to mobile", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int id) {
                                        Log.d("hello","local");
                                        SQLiteDatabase mydatabase = activity_measure.this.getActivity().openOrCreateDatabase("mydb",MODE_PRIVATE,null);
                                        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS record(srno VARCHAR,length VARCHAR,width VARCHAR,thick VARCHAR,cft VARCHAR);");
                                        mydatabase.execSQL("delete from record;");
                                        for (int i = 1; i < count-1; i++) {

                                            TableRow ttrc = (TableRow) tbl.getChildAt(i);
                                            TextView tvv1, tvv2;
                                            EditText eet1, eet2, eet3;
                                            tvv1 = (TextView) ttrc.findViewById(R.id.tvsrno1);
                                            tvv2 = (TextView) ttrc.findViewById(R.id.tvcfeet1);
                                            eet1 = (EditText) ttrc.findViewById(R.id.tvlength1);

                                            eet2 = (EditText) ttrc.findViewById(R.id.tvthick1);
                                            eet3 = (EditText) ttrc.findViewById(R.id.tvheight1);
                                            String srno=tvv1.getText().toString();
                                            String cft=tvv2.getText().toString();
                                            String len=eet1.getText().toString();
                                            String thick=eet2.getText().toString();
                                            String width=eet3.getText().toString();
                                            try {
                                                String sql="INSERT INTO record VALUES('" + srno + "','" + len + "','" + width + "','" + thick + "','" + cft + "');";
                                                Log.d("hello", sql);
                                                mydatabase.execSQL(sql);
                                                Log.d("hello","success");

                                            }
                                            catch(Exception e)
                                            {
                                                Log.e("msg",e.getMessage());
                                            }
                                        }
                                       // Intent in=new Intent(activity_measure.this.getActivity(),LoginActivity.class);
                                        Toast.makeText(activity_measure.this.getActivity(),"Data Saved Locally",Toast.LENGTH_LONG).show();




                                       // activity_measure.this.getActivity().startActivity(in);
                                    }
                                })
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mainjo = new JSONObject();
                                        ja = new JSONArray();
                                         saveflag=true;
                                        for (int i = 1; i < count - 1; i++) {
                                            JSONObject jo = new JSONObject();
                                            TableRow ttrc = (TableRow) tbl.getChildAt(i);
                                            TextView tvv1, tvv2;
                                            EditText eet1, eet2, eet3;
                                            tvv1 = (TextView) ttrc.findViewById(R.id.tvsrno1);
                                            tvv2 = (TextView) ttrc.findViewById(R.id.tvcfeet1);
                                            eet1 = (EditText) ttrc.findViewById(R.id.tvlength1);
                                            eet2 = (EditText) ttrc.findViewById(R.id.tvthick1);
                                            eet3 = (EditText) ttrc.findViewById(R.id.tvheight1);
                                            try {
                                                jo.put("srno", tvv1.getText().toString());
                                                jo.put("cfeet", tvv2.getText().toString());
                                                jo.put("length", eet1.getText().toString());
                                                jo.put("thick", eet2.getText().toString());
                                                jo.put("height", eet3.getText().toString());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            ja.put(jo);
                                        }
                                        try {
                                            mainjo.put("party", spnparty.getSelectedItem().toString());
                                            mainjo.put("srn", lblsrno.getText().toString());
                                            mainjo.put("type", spntype.getSelectedItem().toString());
                                            mainjo.put("total", total.getText().toString());
                                            mainjo.put("array", ja);
                                            Log.e("168",mainjo.toString());
                                        } catch (Exception e) {
                                            Log.e("169",e.getLocalizedMessage());
                                        }

                                        MyTask1 nm = new MyTask1();
                                        try {
                                            nm.execute();
                                        }
                                        catch(Exception e)
                                        {

                                        }
                                    }
                                })
                                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();
                                    }
                                });  */

                     /*   AlertDialog alert = builder.create();
                        alert.setTitle("Submit");
                        alert.show();*/

                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(getActivity(),"Activity Measure 180 : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
    public class MyTask extends AsyncTask<String,String,String> {
        String UR = ip+"/AndroView";
        @Override
        protected void onPreExecute()
        {}
        protected String doInBackground(String... urls) {
            URL url;
            String r = null;
            try

            {  Log.e("476","inbackgoud"+UR);
                url = new URL(UR);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while((inputLine = in.readLine())!= null)
                {
                    response.append(inputLine);

                }
                in.close();
                r = response.toString();

            } catch (Exception e) {
                Log.e("492",e.getMessage());
                e.printStackTrace();
                r=e.getMessage();

            }
            return r;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                Log.e("502",s);

                JSONObject ja = new JSONObject(s);
                String srno = ja.getString("srno");
                JSONArray customer = ja.getJSONArray("customer");
                JSONArray type = ja.getJSONArray("type");

                lblsrno.setText(srno);
                String[] Months = new String[customer.length()];
                for(int i=0;i<customer.length();i++)
                {
                    Months[i]=(String)customer.get(i);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, Months);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnparty.setAdapter(adapter);
                String[] types = new String[type.length()];
                for(int i=0;i<type.length();i++)
                {
                    types[i]=(String)type.get(i);
                }
                ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,types);
                adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spntype.setAdapter(adp);
                Toast.makeText(getActivity(),ip,Toast.LENGTH_SHORT).show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.e("259",ip);
        }
    }
    public class MyTask1 extends AsyncTask<String,String,String> {
        String UR = ip+"/InsertAndroid?json="+mainjo;
        //  String UR = ip+"/InsertAndroid";
        @Override
        protected void onPreExecute()

        {}
        protected String doInBackground(String... urls) {
            URL url;
            String r = null;
            try
            {
                String mainjos = mainjo.toString();
                mainjos=mainjos.replace(" ","_");
                Log.e("UR",mainjos);
                url = new URL(ip+"/InsertAndroid");
                String para = "json="+mainjos;
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(para);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while((inputLine = in.readLine())!= null)
                {
                    response.append(inputLine);

                }
                in.close();
                r = response.toString();
                Log.e("293",r);
            } catch (Exception e) {
                Log.e("ERROR",e.getMessage());
                e.printStackTrace();
                r="error";
            }
            return r;
        }
        @Override
        protected void onPostExecute(String s) {
            // Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
            if(s.equals("error")) {
                Toast.makeText(getActivity(), "No Server Connectivity" +
                        "", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
            }
            Log.e("296",ip+" "+s);
        }

        @Override
        protected void onProgressUpdate(String... values) {

        }
    }
}