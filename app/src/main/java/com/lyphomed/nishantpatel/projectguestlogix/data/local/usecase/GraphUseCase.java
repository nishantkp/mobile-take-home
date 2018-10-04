package com.lyphomed.nishantpatel.projectguestlogix.data.local.usecase;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.schema.AirlinesDatabase;
import com.lyphomed.nishantpatel.projectguestlogix.utils.bws.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;

/**
 * Graph use case which deals with creating a map for BFS algorithm
 */
public class GraphUseCase {
    private AirlinesDatabase mAirlinesDatabase;
    private static Map<String, Node> nodeMap;

    public GraphUseCase(AirlinesDatabase airlinesDatabase) {
        mAirlinesDatabase = airlinesDatabase;
        nodeMap = new HashMap<>();
    }

    /**
     * Use this method to get the Map which contains all the nodes
     * and each node contains information about it's adjacent node
     * which ultimately creates edges
     * <p>
     * NOTE: Don't forget to subscribe flowable on Schedules.io() and observe on
     * AndroidSchedulers.mainThread() to perform whole task on rx schedulers thread
     *
     * @return flowable map
     */
    public Flowable<Map<String, Node>> provideNodesWithEdges() {
        Flowable<List<Airports>> f1 = mAirlinesDatabase.getAirlinesDao().getAllAirports();
        Flowable<List<Routes>> f2 = mAirlinesDatabase.getAirlinesDao().getAllRoutes();

        return Flowable.zip(f1, f2, ((airports, routes) -> {
            for (Airports airport : airports) {
                nodeMap.put(airport.getAirportCode(), new Node(airport.getAirportCode()));
            }
            for (Routes route : routes) {
                if (nodeMap.containsKey(route.getOrigin())) {
                    Node node = nodeMap.get(route.getOrigin());
                    if (nodeMap.get(route.getDestination()) != null) {
                        node.addAdjacent(nodeMap.get(route.getDestination()));
                        nodeMap.remove(route.getOrigin());
                        nodeMap.put(route.getOrigin(), node);
                    }
                }
            }
            return nodeMap;
        }));
    }
}