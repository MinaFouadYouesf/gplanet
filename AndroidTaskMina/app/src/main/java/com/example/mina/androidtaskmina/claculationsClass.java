package com.example.mina.androidtaskmina;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class claculationsClass {
    ArrayList<userObject> users;
    ArrayList<pagesObject> lists;
    int fromUser1 = 100, toUser1 = 0;
    double percentageUserOne = 0.0, percentageUserTwo = 0.0;
    int numOfPages = 70;
    ArrayList<pagesObject> newPages;
    ArrayList<dataObject> percentPages;

    public claculationsClass(ArrayList<pagesObject> lists, ArrayList<userObject> users) {
        this.lists = lists;
        this.users = users;
        newPages = new ArrayList<pagesObject>();
        percentPages = new ArrayList<dataObject>();
    }

    public void claculate() {

        for (int j = 0; j < users.size(); j++) {
            int fromUser1 = 100, toUser1 = 0;
            pagesObject pObject = new pagesObject();


            for (int i = 0; i < lists.size(); i++) {
                if (users.get(j).getId().equals(lists.get(i).getuID())) {
                    if (lists.get(i).getFrom() < fromUser1 && lists.get(i).getTo() > toUser1) {
                        toUser1 = lists.get(i).getTo();
                        fromUser1 = lists.get(i).getFrom();
                    } else if (lists.get(i).getFrom() < toUser1 && lists.get(i).getFrom() > fromUser1) {
                        if (lists.get(i).getTo() > toUser1) {
                            toUser1 = lists.get(i).getTo();
                        }
                    }
                }

            }
            pObject.setFrom(fromUser1);
            pObject.setTo(toUser1);
            pObject.setuID(users.get(j).getId());
            newPages.add(pObject);
        }
    }

    public double claculetePercentage(String id) {

        for (int i = 0; i < newPages.size(); i++) {


            percentageUserOne = (double) ((newPages.get(i).getTo() - newPages.get(i).getFrom()) + 1) / numOfPages;
            dataObject data = new dataObject();
            data.setId(newPages.get(i).getuID());
            data.setPercentage(percentageUserOne);
            percentPages.add(data);
            /* if (id.equals(percentPages.get(i).getId())) {
               return  percentageUserOne;

            }*/

        }
        return getpercentage(id);
    }


    public int getOreder(String id) {

        int position = 0;
        Collections.sort(percentPages, new Comparator<dataObject>() {
            @Override
            public int compare(dataObject o1, dataObject o2) {
                return Double.compare(o2.getPercentage(), o1.getPercentage());
            }
        });

        return getpostinon(id);
    }

    public Double getpercentage(String id) {
        for (int i = 0; i < percentPages.size(); i++) {
            if (id.equals(percentPages.get(i).getId())) {
                return percentPages.get(i).getPercentage();
            }
        }
        return 0.0;
    }

    public int getpostinon(String id) {
        for (int i = 0; i < percentPages.size(); i++) {
            if (id.equals(percentPages.get(i).getId())) {
              return i+1;
            }
        }
        return 0;
    }
}
