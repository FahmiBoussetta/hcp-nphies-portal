import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITaskResponse, defaultValue } from 'app/shared/model/task-response.model';

export const ACTION_TYPES = {
  FETCH_TASKRESPONSE_LIST: 'taskResponse/FETCH_TASKRESPONSE_LIST',
  FETCH_TASKRESPONSE: 'taskResponse/FETCH_TASKRESPONSE',
  CREATE_TASKRESPONSE: 'taskResponse/CREATE_TASKRESPONSE',
  UPDATE_TASKRESPONSE: 'taskResponse/UPDATE_TASKRESPONSE',
  PARTIAL_UPDATE_TASKRESPONSE: 'taskResponse/PARTIAL_UPDATE_TASKRESPONSE',
  DELETE_TASKRESPONSE: 'taskResponse/DELETE_TASKRESPONSE',
  RESET: 'taskResponse/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITaskResponse>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TaskResponseState = Readonly<typeof initialState>;

// Reducer

export default (state: TaskResponseState = initialState, action): TaskResponseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TASKRESPONSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TASKRESPONSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TASKRESPONSE):
    case REQUEST(ACTION_TYPES.UPDATE_TASKRESPONSE):
    case REQUEST(ACTION_TYPES.DELETE_TASKRESPONSE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TASKRESPONSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TASKRESPONSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TASKRESPONSE):
    case FAILURE(ACTION_TYPES.CREATE_TASKRESPONSE):
    case FAILURE(ACTION_TYPES.UPDATE_TASKRESPONSE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TASKRESPONSE):
    case FAILURE(ACTION_TYPES.DELETE_TASKRESPONSE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TASKRESPONSE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TASKRESPONSE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TASKRESPONSE):
    case SUCCESS(ACTION_TYPES.UPDATE_TASKRESPONSE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TASKRESPONSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TASKRESPONSE):
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

const apiUrl = 'api/task-responses';

// Actions

export const getEntities: ICrudGetAllAction<ITaskResponse> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TASKRESPONSE_LIST,
  payload: axios.get<ITaskResponse>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITaskResponse> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TASKRESPONSE,
    payload: axios.get<ITaskResponse>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITaskResponse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TASKRESPONSE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITaskResponse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TASKRESPONSE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITaskResponse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TASKRESPONSE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITaskResponse> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TASKRESPONSE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
