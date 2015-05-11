package com.insthub.ecmobile.model;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.GOODS;
import com.insthub.ecmobile.protocol.SPECIFICATION;
import com.insthub.ecmobile.protocol.SPECIFICATION_VALUE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class GoodDetailDraft {

    public GOODS goodDetail = new GOODS();

    public ArrayList<SPECIFICATION_VALUE> selectedSpecification = new ArrayList<SPECIFICATION_VALUE>();
    public int  goodQuantity = 1;

    private static GoodDetailDraft instance;
    public static GoodDetailDraft getInstance()
    {
        if (instance == null) {
            instance = new GoodDetailDraft();
        }
        return instance;
    }

    public  void clear()
    {
        goodDetail = null;
        selectedSpecification.clear();
        goodQuantity = 1;
    }

    public boolean isHasSpecId(int specId)
    {
        for (int i = 0;i < selectedSpecification.size();i++)
        {
            SPECIFICATION_VALUE selectedSpecificationValue = selectedSpecification.get(i);
            if(Integer.parseInt(selectedSpecificationValue.id) == specId)
            {
                return true;
            }
        }
        return false;
    }

    public boolean isHasSpecName(String specName)
    {
        for (int i = 0;i < selectedSpecification.size();i++)
        {
            SPECIFICATION_VALUE selectedSpecificationValue = selectedSpecification.get(i);
            if(0 == selectedSpecificationValue.specification.name.compareTo(specName))
            {
                return true;
            }
        }
        return false;
    }

    public void removeSpecId(int specId)
    {
        for (int i = 0;i < selectedSpecification.size();i++)
        {
            SPECIFICATION_VALUE selectedSpecificationValue = selectedSpecification.get(i);
            if(Integer.parseInt(selectedSpecificationValue.id) == specId)
            {
                selectedSpecification.remove(i);
                return ;
            }
        }
        return ;
    }

    public void addSelectedSpecification(SPECIFICATION_VALUE specification_value)
    {
        if (0 == specification_value.specification.attr_type.compareTo(SPECIFICATION.MULTIPLE_SELECT))
        {
            if (!isHasSpecId(Integer.parseInt(specification_value.id)))
            {
                selectedSpecification.add(specification_value);
            }

        }
        else
        {
            for (int i = 0;i < selectedSpecification.size();i++)
            {
                SPECIFICATION_VALUE selectedSpecificationValue = selectedSpecification.get(i);
                if ( 0 == selectedSpecificationValue.specification.name.compareTo(specification_value.specification.name))
                {
                    selectedSpecification.remove(i);
                }
            }

            selectedSpecification.add(specification_value);

        }
    }

    public float getTotalPrice()
    {

        if (null == goodDetail || 0 == goodDetail.promote_price)
        {
            return  0;
        }
        float singlePrice =Float.valueOf(goodDetail.promote_price).floatValue();

        for (int i = 0; i < selectedSpecification.size();i++)
        {
            SPECIFICATION_VALUE specification_value = selectedSpecification.get(i);
            singlePrice += Float.valueOf(specification_value.price);
        }

        return singlePrice*goodQuantity;
    }



}
