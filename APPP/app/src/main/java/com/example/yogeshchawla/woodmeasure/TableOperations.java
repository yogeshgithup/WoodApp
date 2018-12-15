package com.example.yogeshchawla.woodmeasure;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Yogesh Chawla on 6/21/2018.
 */

public class TableOperations {
    ArrayList<TablePojo> pojo = new ArrayList<>();
    double total=0;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    public void addRow(TableLayout tl, int srno, final Context ctx, final View fina,TablePojo tablepojo) {
        final TextView tv1,tv2,tv3;
        EditText et1,et2,et3;
        ImageButton ib;
        tv3=(TextView)fina.findViewById(R.id.tvtotal);
        final int[] i = {srno};
        View v = LayoutInflater.from(ctx).inflate(R.layout.layout_table,null);
        TextView textView =(TextView)v.findViewById(R.id.tvsrno1);
        textView.setText(tablepojo.getSr()+"");
        TextView textView1 =(TextView)v.findViewById(R.id.tvcfeet1);
        textView1.setText(0+"");
        ib = (ImageButton)v.findViewById(R.id.tvedit1);
        EditText et = (EditText)v.findViewById(R.id.tvthick1);
        et.setEnabled(false);
        EditText eet = (EditText)v.findViewById(R.id.tvheight1);
        eet.setEnabled(false);
        final EditText eeet = (EditText)v.findViewById(R.id.tvlength1);
        eeet.setEnabled(false);
        et.setInputType(InputType.TYPE_NULL);
        et.setCursorVisible(true);

        eet.setInputType(InputType.TYPE_NULL);
        eet.setCursorVisible(true);

        eeet.setInputType(InputType.TYPE_NULL);
        eeet.setCursorVisible(true);
        et.setText(tablepojo.getThickness()+"");
        eet.setText(tablepojo.getHeight()+"");
        eeet.setText(tablepojo.getLength()+"");
        textView1.setText(tablepojo.getCubicfeet()+"");
        tl.addView(v);

    }
    public void addRow(TableLayout tl, int srno, final Context ctx, final View fina)
    {
        final TextView tv1,tv2,tv3;
        EditText et1,et2,et3;
        ImageButton ib;
        tv3=(TextView)fina.findViewById(R.id.tvtotal);
        final int[] i = {srno};
        View v = LayoutInflater.from(ctx).inflate(R.layout.layout_table,null);
        TextView textView =(TextView)v.findViewById(R.id.tvsrno1);
        textView.setText(srno+"");
        TextView textView1 =(TextView)v.findViewById(R.id.tvcfeet1);
        textView1.setText(0+"");
        ib = (ImageButton)v.findViewById(R.id.tvedit1);
        EditText et = (EditText)v.findViewById(R.id.tvthick1);
        EditText eet = (EditText)v.findViewById(R.id.tvheight1);
        final EditText eeet = (EditText)v.findViewById(R.id.tvlength1);
        et.setSelectAllOnFocus(true);
        et.setInputType(InputType.TYPE_NULL);
        et.setCursorVisible(true);
        eet.setSelectAllOnFocus(true);
        eet.setInputType(InputType.TYPE_NULL);
        eet.setCursorVisible(true);
        eeet.setSelectAllOnFocus(true);
        eeet.setInputType(InputType.TYPE_NULL);
        eeet.setCursorVisible(true);
        try {
            if (i[0] != 1) {
                View vv = tl.getChildAt(i[0] - 1);
                EditText et11 = (EditText) vv.findViewById(R.id.tvthick1);
                EditText eet1 = (EditText) vv.findViewById(R.id.tvheight1);
                EditText eeet1 = (EditText) vv.findViewById(R.id.tvlength1);
                TextView tvvv = (TextView) vv.findViewById(R.id.tvcfeet1);
                et.setText(et11.getText().toString());
                eet.setText(eet1.getText().toString());
                eeet.setText(eeet1.getText().toString());
                //textView1.setText(tvvv.getText().toString());
            }
        }
        catch(Exception e)
        {
            Toast.makeText(ctx,"Table Operations 65 : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }
        //final TableOperations to = new TableOperations();
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView tv1 = (TextView)v.findViewById(R.id.tvsrno1);
//                Toast.makeText(ctx,tv1.getText().toString(),Toast.LENGTH_SHORT).show();
//            }
//        });

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
//                EditText tt = (EditText)v.findViewById(R.id.tvheight1);
                    if (!hasFocus) {
//                Toast.makeText(ctx,tt.getText().toString(),Toast.LENGTH_SHORT).show();
                        View vv = (View) v.getParent();
                        TextView tvv = (TextView) vv.findViewById(R.id.tvsrno1);
                        EditText th = (EditText) vv.findViewById(R.id.tvheight1);
                        EditText tl = (EditText) vv.findViewById(R.id.tvlength1);
                        EditText tt = (EditText) vv.findViewById(R.id.tvthick1);
                        TextView tv = (TextView) vv.findViewById(R.id.tvcfeet1);
                        double itv, ith, itl, itt;
                        int itvv;
                        try {
                            itv = Double.parseDouble(tv.getText().toString());
                        } catch (Exception e) {
                            itv = 0;
                        }
                        try {
                            ith = Double.parseDouble(th.getText().toString());
                        } catch (Exception e) {
                            ith = 0;
                        }
                        try {
                            itl = Double.parseDouble(tl.getText().toString());
                        } catch (Exception e) {
                            itl = 0;
                        }
                        try {
                            itt = Double.parseDouble(tt.getText().toString());
                        } catch (Exception e) {
                            itt = 0;
                        }
                        try {
                            itvv = Integer.parseInt(tvv.getText().toString());
                        } catch (Exception e) {
                            itvv = 1;
                        }
                        if (ith > 0 && itl > 0 && itt > 0 && itv == 0) {
                            double cal = ith * itl;
                            double cal1 = cal * itt;
                            double cal2 = cal1 / 144;
                            TableLayout vvv = (TableLayout) vv.getParent();
                            i[0]++;
                            //Toast.makeText(ctx,"ITV:"+itv+" ITH:"+ith+" ITL:"+itl+" ITT:"+itt+" Cal:"+cal,Toast.LENGTH_LONG).show();
                            String scalc2 = String.format("%.3f", cal2);
                            int index = scalc2.indexOf('.');
                            //if(index!=0)
                            //scalc2=scalc2.substring(0,index+3);
                            tv.setText(scalc2);
                            TablePojo tp = new TablePojo();
                            tp.setSr(i[0] - 1);
                            tp.setCubicfeet(Double.parseDouble(scalc2));
                            tp.setHeight(ith);
                            tp.setLength(itl);
                            tp.setThickness(itt);
                            th.setSelectAllOnFocus(true);
                            tl.setSelectAllOnFocus(true);
                            tt.setSelectAllOnFocus(true);
                            pojo.add(tp);
                            //total=total+Double.parseDouble(scalc2);
                            total = 0.0;
                            setTotal(0.0);
                            for (int i = 1; i < vvv.getChildCount(); i++) {
                                View jhg = vvv.getChildAt(i);
                                TextView tgh = (TextView) jhg.findViewById(R.id.tvcfeet1);
                                total = total + Double.parseDouble(tgh.getText().toString());
                               // Toast.makeText(ctx,Double.parseDouble(tgh.getText().toString())+"",Toast.LENGTH_SHORT).show();
                            }
                            String stotal = String.format("%.3f", total);
                            tv3.setText(stotal);
                            //Toast.makeText(ctx,getPojo().size()+"",Toast.LENGTH_SHORT).show();
//                            if (itvv + 1 == vvv.getChildCount())
//                                addRow(vvv, vvv.getChildCount(), ctx, fina);
                        }
                        if (ith > 0 && itl > 0 && itt > 0 && itv > 0) {
                            double cal = ith * itl;
                            double cal1 = cal * itt;
                            double cal2 = cal1 / 144;
                            //TableLayout vvv = (TableLayout) vv.getParent();
                            //Toast.makeText(ctx,"ITV:"+itv+" ITH:"+ith+" ITL:"+itl+" ITT:"+itt+" Cal:"+cal,Toast.LENGTH_LONG).show();
                            String scalc2 = String.format("%.3f", cal2);
                            int index = scalc2.indexOf('.');
                            tv.setText(scalc2);
                            //if(index!=0)
                            //scalc2=scalc2.substring(0,index+3);
                            //tv.setText(scalc2);
                            total=0.0;
                            TableLayout vvv = (TableLayout) vv.getParent();
                            for (int i = 1; i < vvv.getChildCount(); i++) {
                                View jhg = vvv.getChildAt(i);
                                TextView tgh = (TextView) jhg.findViewById(R.id.tvcfeet1);
                                total = total + Double.parseDouble(tgh.getText().toString());
                                //Toast.makeText(ctx,Double.parseDouble(tgh.getText().toString())+"",Toast.LENGTH_SHORT).show();
                            }
                            String stotal = String.format("%.3f", total);
                            tv3.setText(stotal);
                            th.setSelectAllOnFocus(true);
                            tl.setSelectAllOnFocus(true);
                            tt.setSelectAllOnFocus(true);
//                        total=total-itv;
//                        total=total+Double.parseDouble(scalc2);
                            setTotal(0.0);
                            total = 0.0;


//                            if (itvv + 1 == vvv.getChildCount())
//                                addRow(vvv, vvv.getChildCount(), ctx, fina);
                        }
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(ctx,"Table Operations 188 : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TableRow vv = (TableRow) v.getParent();
                    TextView srrnn = (TextView) vv.getChildAt(0);
                    TextView totaltv = (TextView) vv.getChildAt(4);
                    int srnoo = Integer.parseInt(srrnn.getText().toString());
                    double minus = Double.parseDouble(totaltv.getText().toString());
                    TableLayout vvv = (TableLayout) vv.getParent();
                    double finaltotal = Double.parseDouble(tv3.getText().toString());
                    total = finaltotal - minus;
                    try
                    {

//                        TableLayout vvvv = (TableLayout) vv.getParent();
//                        for (int j = 1; j < vvvv.getChildCount(); j++) {
//                            View jhg = vvvv.getChildAt(j);
//                            TextView tgh = (TextView) jhg.findViewById(R.id.tvcfeet1);
//                            total = total + Double.parseDouble(tgh.getText().toString());
//                        }
                        String stotal = String.format("%.3f", total);
                        tv3.setText(stotal);
                    }
                    catch(Exception e)
                    {
                        Log.e("TableOperations:335",e.getLocalizedMessage());
                    }
//                    if(srnoo!=vvv.getChildCount())
//                    {
//                        for(int i=srnoo+1;i<=vvv.getChildCount();i++)
//                        {
//                            TableRow trr = (TableRow)vvv.getChildAt(i);
//                            TextView tvvv = (TextView)trr.getChildAt(0);
//                            tvvv.setText(i);
//                        }
//                    }
                    if (vvv.getChildCount() == 2) {
                        Toast.makeText(ctx, "Cannot Delete First Row", Toast.LENGTH_SHORT).show();
                    } else {
                        vvv.removeView(vv);
                        ArrayList<TablePojo> pj;
                        TextView ttv = (TextView) vv.findViewById(R.id.tvsrno1);
                        for(int i=1;i<=vvv.getChildCount();i++)
                        {
                            TableRow tvvv = (TableRow)vvv.getChildAt(i);


                            TextView trvv = (TextView)tvvv.getChildAt(0);


                            Log.d("srno=",i+"");
                            trvv.setText(i+"");
                        }
                        TextView ttc = (TextView) vv.findViewById(R.id.tvcfeet1);
//                        total = total - Double.parseDouble(ttc.getText().toString());
//                        String stotal = String.format("%.3f", total);
                      //  tv3.setText(stotal);
                        total = 0;
                        setTotal(0.0);

                        int srd = Integer.parseInt(ttv.getText().toString());
                        pj = getPojo();
                       // Toast.makeText(ctx,"Array "+pj.toString(),Toast.LENGTH_LONG).show();
                        int row = vvv.getChildCount();
                        int index = 0;
                        for (int i = 0; i < pj.size(); i++) {
                            TablePojo tp = pj.get(i);
                            if (tp.getSr() == srd) {
                                index = i;
                                break;
                            }
                            //  Toast.makeText(getActivity(),tp.toString(),Toast.LENGTH_LONG).show();
                        }
                        pj.remove(index);
                        for (int i = srd - 1; i < pj.size(); i++) {
                            pj.get(i).setSr(pj.get(i).getSr() - 1);
                        }
                        int i = 0;

                        for (i = 1; i < row; i++) {
                            View v1 = vvv.getChildAt(i);
                            EditText et1, et2, et3;
                            TextView tv1, tv21;

                            et1 = (EditText) v1.findViewById(R.id.tvthick1);
                            et2 = (EditText) v1.findViewById(R.id.tvlength1);
                            et3 = (EditText) v1.findViewById(R.id.tvheight1);
                            tv1 = (TextView) v1.findViewById(R.id.tvcfeet1);
                            tv21 = (TextView) v1.findViewById(R.id.tvsrno1);
                            if (i != row) {


//                    TableRow v1 = (TableRow)LayoutInflater.from(ctx).inflate(R.layout.layout_table,null);

                                et1.setText(pj.get(i - 1).getThickness() + "");
                                et2.setText(pj.get(i - 1).getLength() + "");
                                et3.setText(pj.get(i - 1).getHeight() + "");
                                tv1.setText(pj.get(i - 1).getCubicfeet() + "");
                                tv21.setText(pj.get(i - 1).getSr() + "");
                                //Toast.makeText(ctx,pj.get(i-1).toString(),Toast.LENGTH_LONG).show();
//                    vvv.addView(v1);
                            } else {
                                tv21.setText(row - 1 + "");
                            }
                        }

//                addRow(vvv,i+1,ctx);

                    }
                }
                catch(Exception e)
                {
                    //Toast.makeText(ctx,"Table Operations 258 : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        eet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            try {
                if (!hasFocus) {
//                Toast.makeText(ctx,tt.getText().toString(),Toast.LENGTH_SHORT).show();
                    View vv = (View) v.getParent();
                    EditText th = (EditText) vv.findViewById(R.id.tvheight1);
                    EditText tl = (EditText) vv.findViewById(R.id.tvlength1);
                    EditText tt = (EditText) vv.findViewById(R.id.tvthick1);
                    TextView tv = (TextView) vv.findViewById(R.id.tvcfeet1);
                    double itv, ith, itl, itt;
                    try {
                        itv = Double.parseDouble(tv.getText().toString());
                    } catch (Exception e) {
                        itv = 0;
                    }
                    try {
                        ith = Double.parseDouble(th.getText().toString());
                    } catch (Exception e) {
                        ith = 0;
                    }
                    try {
                        itl = Double.parseDouble(tl.getText().toString());
                    } catch (Exception e) {
                        itl = 0;
                    }
                    try {
                        itt = Double.parseDouble(tt.getText().toString());
                    } catch (Exception e) {
                        itt = 0;
                    }
                    if (ith > 0 && itl > 0 && itt > 0 && itv >= 0) {
                        double cal = ith * itl;
                        double cal1 = cal * itt;
                        double cal2 = cal1 / 144;
                        //TableLayout vvv = (TableLayout) vv.getParent();
                        //Toast.makeText(ctx,"ITV:"+itv+" ITH:"+ith+" ITL:"+itl+" ITT:"+itt+" Cal:"+cal,Toast.LENGTH_LONG).show();
                        String scalc2 = String.format("%.3f", cal2);
                        int index = scalc2.indexOf('.');
                        //if(index!=0)
                        //scalc2=scalc2.substring(0,index+3);
//                        total=total+Double.parseDouble(scalc2);
//                        tv3.setText(total+"");
//                        total=total-itv;
//                        total=total+Double.parseDouble(scalc2);
                        th.setSelectAllOnFocus(true);
                        tl.setSelectAllOnFocus(true);
                        tt.setSelectAllOnFocus(true);
                        setTotal(0.0);
                        total = 0;
//                        TableLayout vvv = (TableLayout) vv.getParent();
//                        for (int i = 1; i < vvv.getChildCount(); i++) {
//                            View jhg = vvv.getChildAt(i);
//                            TextView tgh = (TextView) jhg.findViewById(R.id.tvcfeet1);
//                            total = total + Double.parseDouble(tgh.getText().toString());
//                        }
//                        String stotal = String.format("%.3f", total);
//                        tv3.setText(stotal);

                        tv.setText(scalc2);
//                        if(i[0]==vvv.getChildCount())
//                            addRow(vvv,vvv.getChildCount(),ctx,fina);
                    }
                    if (ith > 0 && itl > 0 && itt > 0 && itv == 0) {
                        double cal = ith * itl;
                        double cal1 = cal * itt;
                        double cal2 = cal1 / 144;
                        TableLayout vvv = (TableLayout) vv.getParent();
                        i[0]++;
                        //Toast.makeText(ctx,"ITV:"+itv+" ITH:"+ith+" ITL:"+itl+" ITT:"+itt+" Cal:"+cal,Toast.LENGTH_LONG).show();
                        String scalc2 = String.format("%.3f", cal2);
                        int index = scalc2.indexOf('.');
                        //if(index!=0)
                        //scalc2=scalc2.substring(0,index+3);
                        TablePojo tp = new TablePojo();
                        tp.setSr(i[0] - 1);
                        tp.setCubicfeet(Double.parseDouble(scalc2));
                        tp.setHeight(ith);
                        tp.setLength(itl);
                        tp.setThickness(itt);
                        th.setSelectAllOnFocus(true);
                        tl.setSelectAllOnFocus(true);
                        tt.setSelectAllOnFocus(true);
                        pojo.add(tp);
                        tv.setText(scalc2);
                        //total=total+Double.parseDouble(scalc2);
                        setTotal(0.0);
                        total = 0;
//                        for (int i = 1; i < vvv.getChildCount(); i++) {
//                            View jhg = vvv.getChildAt(i);
//                            TextView tgh = (TextView) jhg.findViewById(R.id.tvcfeet1);
//                            total = total + Double.parseDouble(tgh.getText().toString());
//                        }
//                        String stotal = String.format("%.3f", total);
//                        tv3.setText(stotal);//                        if(i[0]==vvv.getChildCount())
//                        addRow(vvv,vvv.getChildCount(),ctx,fina);
                    }
                }
            }
            catch(Exception e)
            {
                Toast.makeText(ctx,"Table Operations 364 : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
            }
        });
        eeet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (!hasFocus) {
//                Toast.makeText(ctx,tt.getText().toString(),Toast.LENGTH_SHORT).show();
                        View vv = (View) v.getParent();
                        EditText th = (EditText) vv.findViewById(R.id.tvheight1);
                        EditText tl = (EditText) vv.findViewById(R.id.tvlength1);
                        EditText tt = (EditText) vv.findViewById(R.id.tvthick1);
                        TextView tv = (TextView) vv.findViewById(R.id.tvcfeet1);
                        double itv, ith, itl, itt;
                        try {
                            itv = Double.parseDouble(tv.getText().toString());
                        } catch (Exception e) {
                            itv = 0;
                        }
                        try {
                            ith = Double.parseDouble(th.getText().toString());
                        } catch (Exception e) {
                            ith = 0;
                        }
                        try {
                            itl = Double.parseDouble(tl.getText().toString());
                        } catch (Exception e) {
                            itl = 0;
                        }
                        try {
                            itt = Double.parseDouble(tt.getText().toString());
                        } catch (Exception e) {
                            itt = 0;
                        }
                        if (ith > 0 && itl > 0 && itt > 0 && itv >= 0) {
                            double cal = ith * itl;
                            double cal1 = cal * itt;
                            double cal2 = cal1 / 144;

                            //TableLayout vvv = (TableLayout) vv.getParent();
                            //Toast.makeText(ctx,"ITV:"+itv+" ITH:"+ith+" ITL:"+itl+" ITT:"+itt+" Cal:"+cal,Toast.LENGTH_LONG).show();
                            String scalc2 = String.format("%.3f", cal2);
                            int index = scalc2.indexOf('.');
                            //if(index!=0)
                            //scalc2=scalc2.substring(0,index+3);
//                        total=total+Double.parseDouble(scalc2);
//                        tv3.setText(total+"");
//                        total=total-itv;
//                        total=total+Double.parseDouble(scalc2);
//                        String stotal = String.format("%.3f",total);
//                        tv3.setText(stotal);
                            setTotal(0.0);
                            total = 0;
//                            TableLayout vvv = (TableLayout) vv.getParent();
//                            for (int i = 1; i < vvv.getChildCount(); i++) {
//                                View jhg = vvv.getChildAt(i);
//                                TextView tgh = (TextView) jhg.findViewById(R.id.tvcfeet1);
//                                total = total + Double.parseDouble(tgh.getText().toString());
//                            }
//                            String stotal = String.format("%.3f", total);
//                            tv3.setText(stotal);
                            tv.setText(scalc2);
                            //TableLayout vvv = (TableLayout) vv.getParent();
//                        if(i[0]==vvv.getChildCount())
//                            addRow(vvv,vvv.getChildCount(),ctx,fina);

                        }
                        if (ith > 0 && itl > 0 && itt > 0 && itv == 0) {
                            double cal = ith * itl;
                            double cal1 = cal * itt;
                            double cal2 = cal1 / 144;
                            TableLayout vvv = (TableLayout) vv.getParent();
                            i[0]++;
                            //Toast.makeText(ctx,"ITV:"+itv+" ITH:"+ith+" ITL:"+itl+" ITT:"+itt+" Cal:"+cal,Toast.LENGTH_LONG).show();
                            String scalc2 = String.format("%.3f", cal2);
                            int index = scalc2.indexOf('.');
                            //if(index!=0)
                            //scalc2=scalc2.substring(0,index+3);
                            TablePojo tp = new TablePojo();
                            tp.setSr(i[0] - 1);
                            tp.setCubicfeet(Double.parseDouble(scalc2));
                            tp.setHeight(ith);
                            tp.setLength(itl);
                            tp.setThickness(itt);
                            th.setSelectAllOnFocus(true);
                            tl.setSelectAllOnFocus(true);
                            tt.setSelectAllOnFocus(true);
                            pojo.add(tp);
                            tv.setText(scalc2);
                            //total=total+Double.parseDouble(scalc2);
                            setTotal(0.0);
                            total = 0;
                            //TableLayout vvv = (TableLayout) vv.getParent();
//                            for (int i = 1; i < vvv.getChildCount(); i++) {
//                                View jhg = vvv.getChildAt(i);
//                                TextView tgh = (TextView) jhg.findViewById(R.id.tvcfeet1);
//                                total = total + Double.parseDouble(tgh.getText().toString());
//                            }
//                            String stotal = String.format("%.3f", total);
//                            tv3.setText(stotal);
//                        if(i[0]==vvv.getChildCount())
//                        addRow(vvv,vvv.getChildCount(),ctx,fina);
                        }
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(ctx,"Table Operations 473 : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        tl.addView(v);
}

public ArrayList<TablePojo> getPojo()
{
 return pojo;
}
}
