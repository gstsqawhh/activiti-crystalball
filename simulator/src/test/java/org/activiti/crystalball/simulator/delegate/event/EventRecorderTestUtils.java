package org.activiti.crystalball.simulator.delegate.event;

import org.activiti.crystalball.simulator.SimulationEventHandler;
import org.activiti.crystalball.simulator.delegate.event.impl.ProcessInstanceCreateTransformer;
import org.activiti.crystalball.simulator.delegate.event.impl.UserTaskCompleteTransformer;
import org.activiti.crystalball.simulator.impl.StartProcessEventHandler;
import org.activiti.crystalball.simulator.impl.UserTaskCompleteEventHandler;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventRecorderTestUtils {
  public static final boolean DO_NOT_CLOSE_PROCESS_ENGINE = false;
  // Process instance start event
  private static final String PROCESS_INSTANCE_START_EVENT_TYPE = "PROCESS_INSTANCE_START";
  private static final String PROCESS_DEFINITION_ID_KEY = "processDefinitionId";
  private static final String BUSINESS_KEY = "businessKey";
  private static final String VARIABLES_KEY = "variables";
  // User task completed event
  private static final String USER_TASK_COMPLETED_EVENT_TYPE = "USER_TASK_COMPLETED";


  protected EventRecorderTestUtils() {
    // static class
  }

  public static Map<String, SimulationEventHandler> getHandlers() {
    Map<String, SimulationEventHandler> handlers = new HashMap<String, SimulationEventHandler>();
    handlers.put(PROCESS_INSTANCE_START_EVENT_TYPE, new StartProcessEventHandler(PROCESS_DEFINITION_ID_KEY, BUSINESS_KEY, VARIABLES_KEY));
    handlers.put(USER_TASK_COMPLETED_EVENT_TYPE, new UserTaskCompleteEventHandler());
    return handlers;
  }

  public static List<ActivitiEventToSimulationEventTransformer> getTransformers() {
    List<ActivitiEventToSimulationEventTransformer> transformers = new ArrayList<ActivitiEventToSimulationEventTransformer>();
    transformers.add( new ProcessInstanceCreateTransformer(PROCESS_INSTANCE_START_EVENT_TYPE, PROCESS_DEFINITION_ID_KEY, BUSINESS_KEY, VARIABLES_KEY));
    transformers.add(new UserTaskCompleteTransformer(USER_TASK_COMPLETED_EVENT_TYPE));
    return transformers;
  }

  public static void closeProcessEngine(ProcessEngine processEngine, ActivitiEventListener listener) {
    if (listener != null) {
      final ProcessEngineConfigurationImpl processEngineConfiguration = ((ProcessEngineImpl) processEngine).getProcessEngineConfiguration();
      processEngineConfiguration.getEventDispatcher().removeEventListener(listener);
    }
    processEngine.close();
  }


}