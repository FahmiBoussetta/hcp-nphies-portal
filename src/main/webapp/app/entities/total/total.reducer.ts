import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITotal, defaultValue } from 'app/shared/model/total.model';

export const ACTION_TYPES = {
  FETCH_TOTAL_LIST: 'total/FETCH_TOTAL_LIST',
  FETCH_TOTAL: 'total/FETCH_TOTAL',
  CREATE_TOTAL: 'total/CREATE_TOTAL',
  UPDATE_TOTAL: 'total/UPDATE_TOTAL',
  PARTIAL_UPDATE_TOTAL: 'total/PARTIAL_UPDATE_TOTAL',
  DELETE_TOTAL: 'total/DELETE_TOTAL',
  RESET: 'total/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITotal>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TotalState = Readonly<typeof initialState>;

// Reducer

export default (state: TotalState = initialState, action): TotalState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TOTAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TOTAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TOTAL):
    case REQUEST(ACTION_TYPES.UPDATE_TOTAL):
    case REQUEST(ACTION_TYPES.DELETE_TOTAL):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TOTAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TOTAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TOTAL):
    case FAILURE(ACTION_TYPES.CREATE_TOTAL):
    case FAILURE(ACTION_TYPES.UPDATE_TOTAL):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TOTAL):
    case FAILURE(ACTION_TYPES.DELETE_TOTAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TOTAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TOTAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TOTAL):
    case SUCCESS(ACTION_TYPES.UPDATE_TOTAL):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TOTAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TOTAL):
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

const apiUrl = 'api/totals';

// Actions

export const getEntities: ICrudGetAllAction<ITotal> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TOTAL_LIST,
  payload: axios.get<ITotal>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITotal> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TOTAL,
    payload: axios.get<ITotal>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITotal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TOTAL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITotal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TOTAL,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITotal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TOTAL,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITotal> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TOTAL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
