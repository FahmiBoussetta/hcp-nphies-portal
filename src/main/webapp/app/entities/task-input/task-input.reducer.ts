import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITaskInput, defaultValue } from 'app/shared/model/task-input.model';

export const ACTION_TYPES = {
  FETCH_TASKINPUT_LIST: 'taskInput/FETCH_TASKINPUT_LIST',
  FETCH_TASKINPUT: 'taskInput/FETCH_TASKINPUT',
  CREATE_TASKINPUT: 'taskInput/CREATE_TASKINPUT',
  UPDATE_TASKINPUT: 'taskInput/UPDATE_TASKINPUT',
  PARTIAL_UPDATE_TASKINPUT: 'taskInput/PARTIAL_UPDATE_TASKINPUT',
  DELETE_TASKINPUT: 'taskInput/DELETE_TASKINPUT',
  RESET: 'taskInput/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITaskInput>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TaskInputState = Readonly<typeof initialState>;

// Reducer

export default (state: TaskInputState = initialState, action): TaskInputState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TASKINPUT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TASKINPUT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TASKINPUT):
    case REQUEST(ACTION_TYPES.UPDATE_TASKINPUT):
    case REQUEST(ACTION_TYPES.DELETE_TASKINPUT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TASKINPUT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TASKINPUT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TASKINPUT):
    case FAILURE(ACTION_TYPES.CREATE_TASKINPUT):
    case FAILURE(ACTION_TYPES.UPDATE_TASKINPUT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TASKINPUT):
    case FAILURE(ACTION_TYPES.DELETE_TASKINPUT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TASKINPUT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TASKINPUT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TASKINPUT):
    case SUCCESS(ACTION_TYPES.UPDATE_TASKINPUT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TASKINPUT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TASKINPUT):
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

const apiUrl = 'api/task-inputs';

// Actions

export const getEntities: ICrudGetAllAction<ITaskInput> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TASKINPUT_LIST,
  payload: axios.get<ITaskInput>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITaskInput> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TASKINPUT,
    payload: axios.get<ITaskInput>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITaskInput> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TASKINPUT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITaskInput> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TASKINPUT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITaskInput> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TASKINPUT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITaskInput> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TASKINPUT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
