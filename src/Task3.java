import PetriObj.*;

import java.util.ArrayList;
import java.util.Objects;

public class Task3 {

    private static final int timeModeling = 1000;

    private static final double avgOrderArrival = 0.2, avgStorageCheck = 4, avgOrderDeliveryTime = 3;

    public static void main(String[] args) throws ExceptionInvalidTimeDelay {
        PetriObjModel model = getModel();
        model.setIsProtokol(false);
        model.go(timeModeling);

        System.out.println("Input values:");
        System.out.println("Average Order Arrival: " + avgOrderArrival);
        System.out.println("Average Storage Check Interval: " + avgStorageCheck);
        System.out.println("Average Order Delivery Time: " + avgOrderDeliveryTime);
        System.out.println("\nOutput values:");

        for (PetriP p : model.getListObj().get(0).getNet().getListP()) {
            if (Objects.equals(p.getName(), "Вдов. попит")) {
                System.out.printf("Вдоволений попит: %d \n", p.getMark());
            } else if (Objects.equals(p.getName(), "Невдов. попит")) {
                System.out.printf("Невдов. попит: %d \n", p.getMark());
            } else if (Objects.equals(p.getName(), "Запас")) {
                System.out.printf("Середній стан запасу: %f \n", p.getMean());
            } else if (Objects.equals(p.getName(), "К-сть поповнень")) {
                System.out.printf("К-сть поповнень: %d \n", p.getMark());
            }
        }

    }

    public static PetriObjModel getModel() throws ExceptionInvalidTimeDelay {
        ArrayList<PetriSim> list = new ArrayList<>();
        list.add(new PetriSim(getNet(avgOrderArrival, avgStorageCheck, avgOrderDeliveryTime)));
        return new PetriObjModel(list);
    }


    public static PetriNet getNet(double avgOrderArrival, double avgStorageCheck, double avgOrderDeliveryTime) throws ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P1", 1));
        d_P.add(new PetriP("P2", 0));
        d_P.add(new PetriP("P4", 0));
        d_P.add(new PetriP("Невдов. попит", 0));
        d_P.add(new PetriP("Вдов. попит", 0));
        d_P.add(new PetriP("Запас", 72));
        d_P.add(new PetriP("P8", 1));
        d_P.add(new PetriP("P9", 0));
        d_P.add(new PetriP("Стан запасу задов.", 0));
        d_P.add(new PetriP("К-сть поповнень", 0));
        d_P.add(new PetriP("P1", 0));
        d_T.add(new PetriT("T1", avgOrderArrival));
        d_T.get(0).setDistribution("exp", d_T.get(0).getTimeServ());
        d_T.get(0).setParamDeviation(0.0);
        d_T.add(new PetriT("Товар в наявн.", 0.0));
        d_T.get(1).setPriority(1);
        d_T.add(new PetriT("Повторне замовл.", 0.0));
        d_T.get(2).setProbability(0.2);
        d_T.add(new PetriT("Інший маг.", 0.0));
        d_T.get(3).setProbability(0.8);
        d_T.add(new PetriT("T7", 0.0));
        d_T.add(new PetriT("T8", avgStorageCheck));
        d_T.add(new PetriT("Дост. запас", 0.0));
        d_T.get(6).setPriority(1);
        d_T.add(new PetriT("Недост. запас", avgOrderDeliveryTime));
        d_T.add(new PetriT("Товар відс.", 0.0));
        d_In.add(new ArcIn(d_P.get(1), d_T.get(1), 1));
        d_In.add(new ArcIn(d_P.get(5), d_T.get(1), 1));
        d_In.add(new ArcIn(d_P.get(10), d_T.get(3), 1));
        d_In.add(new ArcIn(d_P.get(6), d_T.get(5), 1));
        d_In.add(new ArcIn(d_P.get(7), d_T.get(6), 1));
        d_In.add(new ArcIn(d_P.get(5), d_T.get(6), 19));
        d_In.get(5).setInf(true);
        d_In.add(new ArcIn(d_P.get(1), d_T.get(8), 1));
        d_In.add(new ArcIn(d_P.get(2), d_T.get(4), 1));
        d_In.add(new ArcIn(d_P.get(5), d_T.get(4), 1));
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_In.add(new ArcIn(d_P.get(10), d_T.get(2), 1));
        d_In.add(new ArcIn(d_P.get(7), d_T.get(7), 1));
        d_Out.add(new ArcOut(d_T.get(1), d_P.get(4), 1));
        d_Out.add(new ArcOut(d_T.get(3), d_P.get(3), 1));
        d_Out.add(new ArcOut(d_T.get(5), d_P.get(6), 1));
        d_Out.add(new ArcOut(d_T.get(5), d_P.get(7), 1));
        d_Out.add(new ArcOut(d_T.get(6), d_P.get(8), 1));
        d_Out.add(new ArcOut(d_T.get(8), d_P.get(10), 1));
        d_Out.add(new ArcOut(d_T.get(4), d_P.get(4), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(0), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(2), d_P.get(2), 1));
        d_Out.add(new ArcOut(d_T.get(7), d_P.get(9), 1));
        d_Out.add(new ArcOut(d_T.get(7), d_P.get(5), 18));
        PetriNet d_Net = new PetriNet("task3", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }
}

