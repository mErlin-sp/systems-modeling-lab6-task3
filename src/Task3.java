import PetriObj.*;

import java.util.ArrayList;
import java.util.Objects;

public class Task3 {

    private static final int timeModeling = 5000;

    private static final double avgOrderArrival = 0.2, avgStorageCheck = 4, avgOrderDeliveryTime = 1;

    public static void main(String[] args) throws ExceptionInvalidTimeDelay {
        PetriObjModel model = getModel();
        model.setIsProtokol(false);
        model.go(timeModeling);

        System.out.println("Time modeling: " + timeModeling);
        System.out.println();
        System.out.println("Input values:");
        System.out.println("Average Order Arrival: " + avgOrderArrival);
        System.out.println("Average Storage Check Interval: " + avgStorageCheck);
        System.out.println("Average Order Delivery Time: " + avgOrderDeliveryTime);
        System.out.println("\nOutput values:");

        for (PetriP p : model.getListObj().getFirst().getNet().getListP()) {
            if (Objects.equals(p.getName(), "К-сть виконаних замовлень")) {
                System.out.printf("К-сть виконаних замовлень: %d \n", p.getMark());
            } else if (Objects.equals(p.getName(), "К-сть невиконаних замовлень")) {
                System.out.printf("К-сть невиконаних замовлень: %d \n", p.getMark());
            } else if (Objects.equals(p.getName(), "Запас")) {
                System.out.printf("Середній стан запасу: %f \n", p.getMean());
            } else if (Objects.equals(p.getName(), "К-сть виконаних поповнень")) {
                System.out.printf("К-сть виконаних поповнень: %d \n", p.getMark());
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
        d_P.add(new PetriP("Черга замовлень", 0));
        d_P.add(new PetriP("Недостаток", 0));
        d_P.add(new PetriP("Запас", 72));
        d_P.add(new PetriP("Поточний стан запасу", 72));
        d_P.add(new PetriP("К-сть виконаних замовлень", 0));
        d_P.add(new PetriP("К-сть невиконаних замовлень", 0));
        d_P.add(new PetriP("К-сть повторних замовлень", 0));
        d_P.add(new PetriP("К-сть виконаних поповнень", 0));
        d_P.add(new PetriP("P12", 0));
        d_P.add(new PetriP("P13", 0));
        d_P.add(new PetriP("P14", 0));
        d_P.add(new PetriP("P15", 0));
        d_P.add(new PetriP("К-сть замовленого товару", 0));
        d_P.add(new PetriP("P17", 1));
        d_P.add(new PetriP("Достатній запас", 0));
        d_P.add(new PetriP("Черга невиконаних замовлень", 0));
        d_T.add(new PetriT("Надходження замовлень", avgOrderArrival));
        d_T.getFirst().setDistribution("exp", d_T.getFirst().getTimeServ());
        d_T.get(0).setParamDeviation(0.0);
        d_T.add(new PetriT("Клієнт стає в чергу", 0.0));
        d_T.get(1).setPriority(1);
        d_T.add(new PetriT("Повторне замовлення", 0.0));
        d_T.get(2).setProbability(0.2);
        d_T.add(new PetriT("Клієнт стає в чергу невиконаних замовлень", 0.0));
        d_T.get(3).setProbability(0.8);
        d_T.add(new PetriT("Оформлення замовлення", 0.0));
        d_T.add(new PetriT("Завершення розвантаження замовлення", 0.0));
        d_T.add(new PetriT("Розвантаження замовлення", 0.0));
        d_T.get(6).setPriority(1);
        d_T.add(new PetriT("Доставка замовлення", avgOrderDeliveryTime));
        d_T.add(new PetriT("Додавання товару", 0.0));
        d_T.get(8).setPriority(1);
        d_T.add(new PetriT("Оформлення замовлення на склад", 0.0));
        d_T.add(new PetriT("Надходження вимоги перегляду стану запасу", avgStorageCheck));
        d_T.add(new PetriT("T12", 0.0));
        d_T.get(11).setPriority(1);
        d_T.add(new PetriT("T13", 0.0));
        d_In.add(new ArcIn(d_P.get(1), d_T.get(3), 1));
        d_In.add(new ArcIn(d_P.get(2), d_T.get(4), 1));
        d_In.add(new ArcIn(d_P.get(5), d_T.get(4), 1));
        d_In.add(new ArcIn(d_P.get(4), d_T.get(4), 1));
        d_In.add(new ArcIn(d_P.get(12), d_T.get(7), 1));
        d_In.add(new ArcIn(d_P.get(5), d_T.get(11), 19));
        d_In.get(5).setInf(true);
        d_In.add(new ArcIn(d_P.get(13), d_T.get(11), 1));
        d_In.add(new ArcIn(d_P.getFirst(), d_T.get(0), 1));
        d_In.add(new ArcIn(d_P.get(10), d_T.get(6), 1));
        d_In.add(new ArcIn(d_P.get(11), d_T.get(6), 1));
        d_In.add(new ArcIn(d_P.get(5), d_T.get(12), 1));
        d_In.add(new ArcIn(d_P.get(17), d_T.get(12), 1));
        d_In.add(new ArcIn(d_P.get(1), d_T.get(2), 1));
        d_In.add(new ArcIn(d_P.get(12), d_T.get(8), 1));
        d_In.add(new ArcIn(d_P.get(3), d_T.get(8), 1));
        d_In.add(new ArcIn(d_P.get(13), d_T.get(9), 1));
        d_In.add(new ArcIn(d_P.get(15), d_T.get(10), 1));
        d_In.add(new ArcIn(d_P.get(1), d_T.get(1), 1));
        d_In.add(new ArcIn(d_P.get(4), d_T.get(1), 1));
        d_In.get(18).setInf(true);
        d_In.add(new ArcIn(d_P.get(10), d_T.get(5), 1));
        d_Out.add(new ArcOut(d_T.get(3), d_P.get(17), 1));
        d_Out.add(new ArcOut(d_T.get(4), d_P.get(3), 1));
        d_Out.add(new ArcOut(d_T.get(4), d_P.get(6), 1));
        d_Out.add(new ArcOut(d_T.get(7), d_P.get(10), 1));
        d_Out.add(new ArcOut(d_T.get(11), d_P.get(16), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(0), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(6), d_P.get(4), 1));
        d_Out.add(new ArcOut(d_T.get(6), d_P.get(10), 1));
        d_Out.add(new ArcOut(d_T.get(12), d_P.get(7), 1));
        d_Out.add(new ArcOut(d_T.get(2), d_P.get(2), 1));
        d_Out.add(new ArcOut(d_T.get(2), d_P.get(8), 1));
        d_Out.add(new ArcOut(d_T.get(8), d_P.get(11), 1));
        d_Out.add(new ArcOut(d_T.get(8), d_P.get(5), 1));
        d_Out.add(new ArcOut(d_T.get(8), d_P.get(12), 1));
        d_Out.add(new ArcOut(d_T.get(8), d_P.get(14), 1));
        d_Out.add(new ArcOut(d_T.get(9), d_P.get(12), 1));
        d_Out.add(new ArcOut(d_T.get(10), d_P.get(13), 1));
        d_Out.add(new ArcOut(d_T.get(10), d_P.get(15), 1));
        d_Out.add(new ArcOut(d_T.get(1), d_P.get(2), 1));
        d_Out.add(new ArcOut(d_T.get(5), d_P.get(9), 1));
        PetriNet d_Net = new PetriNet("task3", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

}

