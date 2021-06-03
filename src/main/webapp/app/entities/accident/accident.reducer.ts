import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAccident, defaultValue } from 'app/shared/model/accident.model';

export const ACTION_TYPES = {
  FETCH_ACCIDENT_LIST: 'accident/FETCH_ACCIDENT_LIST',
  FETCH_ACCIDENT: 'accident/FETCH_ACCIDENT',
  CREATE_ACCIDENT: 'accident/CREATE_ACCIDENT',
  UPDATE_ACCIDENT: 'accident/UPDATE_ACCIDENT',
  PARTIAL_UPDATE_ACCIDENT: 'accident/PARTIAL_UPDATE_ACCIDENT',
  DELETE_ACCIDENT: 'accident/DELETE_ACCIDENT',
  RESET: 'accident/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAccident>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AccidentState = Readonly<typeof initialState>;

// Reducer

export default (state: AccidentState = initialState, action): AccidentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACCIDENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACCIDENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ACCIDENT):
    case REQUEST(ACTION_TYPES.UPDATE_ACCIDENT):
    case REQUEST(ACTION_TYPES.DELETE_ACCIDENT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ACCIDENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ACCIDENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACCIDENT):
    case FAILURE(ACTION_TYPES.CREATE_ACCIDENT):
    case FAILURE(ACTION_TYPES.UPDATE_ACCIDENT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ACCIDENT):
    case FAILURE(ACTION_TYPES.DELETE_ACCIDENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACCIDENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACCIDENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACCIDENT):
    case SUCCESS(ACTION_TYPES.UPDATE_ACCIDENT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ACCIDENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACCIDENT):
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

const apiUrl = 'api/accidents';

// Actions

export const getEntities: ICrudGetAllAction<IAccident> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ACCIDENT_LIST,
  payload: axios.get<IAccident>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAccident> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACCIDENT,
    payload: axios.get<IAccident>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAccident> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACCIDENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAccident> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACCIDENT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAccident> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ACCIDENT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAccident> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACCIDENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
