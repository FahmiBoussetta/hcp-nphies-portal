import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITaskOutput, defaultValue } from 'app/shared/model/task-output.model';

export const ACTION_TYPES = {
  FETCH_TASKOUTPUT_LIST: 'taskOutput/FETCH_TASKOUTPUT_LIST',
  FETCH_TASKOUTPUT: 'taskOutput/FETCH_TASKOUTPUT',
  CREATE_TASKOUTPUT: 'taskOutput/CREATE_TASKOUTPUT',
  UPDATE_TASKOUTPUT: 'taskOutput/UPDATE_TASKOUTPUT',
  PARTIAL_UPDATE_TASKOUTPUT: 'taskOutput/PARTIAL_UPDATE_TASKOUTPUT',
  DELETE_TASKOUTPUT: 'taskOutput/DELETE_TASKOUTPUT',
  RESET: 'taskOutput/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITaskOutput>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TaskOutputState = Readonly<typeof initialState>;

// Reducer

export default (state: TaskOutputState = initialState, action): TaskOutputState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TASKOUTPUT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TASKOUTPUT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TASKOUTPUT):
    case REQUEST(ACTION_TYPES.UPDATE_TASKOUTPUT):
    case REQUEST(ACTION_TYPES.DELETE_TASKOUTPUT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TASKOUTPUT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TASKOUTPUT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TASKOUTPUT):
    case FAILURE(ACTION_TYPES.CREATE_TASKOUTPUT):
    case FAILURE(ACTION_TYPES.UPDATE_TASKOUTPUT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TASKOUTPUT):
    case FAILURE(ACTION_TYPES.DELETE_TASKOUTPUT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TASKOUTPUT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TASKOUTPUT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TASKOUTPUT):
    case SUCCESS(ACTION_TYPES.UPDATE_TASKOUTPUT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TASKOUTPUT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TASKOUTPUT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/task-outputs';

// Actions

export const getEntities: ICrudGetAllAction<ITaskOutput> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TASKOUTPUT_LIST,
  payload: axios.get<ITaskOutput>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITaskOutput> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TASKOUTPUT,
    payload: axios.get<ITaskOutput>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITaskOutput> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TASKOUTPUT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITaskOutput> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TASKOUTPUT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITaskOutput> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TASKOUTPUT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITaskOutput> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TASKOUTPUT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
