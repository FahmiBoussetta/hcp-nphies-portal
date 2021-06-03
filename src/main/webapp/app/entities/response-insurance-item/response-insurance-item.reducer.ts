import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IResponseInsuranceItem, defaultValue } from 'app/shared/model/response-insurance-item.model';

export const ACTION_TYPES = {
  FETCH_RESPONSEINSURANCEITEM_LIST: 'responseInsuranceItem/FETCH_RESPONSEINSURANCEITEM_LIST',
  FETCH_RESPONSEINSURANCEITEM: 'responseInsuranceItem/FETCH_RESPONSEINSURANCEITEM',
  CREATE_RESPONSEINSURANCEITEM: 'responseInsuranceItem/CREATE_RESPONSEINSURANCEITEM',
  UPDATE_RESPONSEINSURANCEITEM: 'responseInsuranceItem/UPDATE_RESPONSEINSURANCEITEM',
  PARTIAL_UPDATE_RESPONSEINSURANCEITEM: 'responseInsuranceItem/PARTIAL_UPDATE_RESPONSEINSURANCEITEM',
  DELETE_RESPONSEINSURANCEITEM: 'responseInsuranceItem/DELETE_RESPONSEINSURANCEITEM',
  RESET: 'responseInsuranceItem/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IResponseInsuranceItem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ResponseInsuranceItemState = Readonly<typeof initialState>;

// Reducer

export default (state: ResponseInsuranceItemState = initialState, action): ResponseInsuranceItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RESPONSEINSURANCEITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RESPONSEINSURANCEITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RESPONSEINSURANCEITEM):
    case REQUEST(ACTION_TYPES.UPDATE_RESPONSEINSURANCEITEM):
    case REQUEST(ACTION_TYPES.DELETE_RESPONSEINSURANCEITEM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_RESPONSEINSURANCEITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RESPONSEINSURANCEITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RESPONSEINSURANCEITEM):
    case FAILURE(ACTION_TYPES.CREATE_RESPONSEINSURANCEITEM):
    case FAILURE(ACTION_TYPES.UPDATE_RESPONSEINSURANCEITEM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_RESPONSEINSURANCEITEM):
    case FAILURE(ACTION_TYPES.DELETE_RESPONSEINSURANCEITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESPONSEINSURANCEITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESPONSEINSURANCEITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RESPONSEINSURANCEITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_RESPONSEINSURANCEITEM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_RESPONSEINSURANCEITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RESPONSEINSURANCEITEM):
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

const apiUrl = 'api/response-insurance-items';

// Actions

export const getEntities: ICrudGetAllAction<IResponseInsuranceItem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RESPONSEINSURANCEITEM_LIST,
  payload: axios.get<IResponseInsuranceItem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IResponseInsuranceItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RESPONSEINSURANCEITEM,
    payload: axios.get<IResponseInsuranceItem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IResponseInsuranceItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RESPONSEINSURANCEITEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IResponseInsuranceItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RESPONSEINSURANCEITEM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IResponseInsuranceItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_RESPONSEINSURANCEITEM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IResponseInsuranceItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RESPONSEINSURANCEITEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
