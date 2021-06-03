import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDetailItem, defaultValue } from 'app/shared/model/detail-item.model';

export const ACTION_TYPES = {
  FETCH_DETAILITEM_LIST: 'detailItem/FETCH_DETAILITEM_LIST',
  FETCH_DETAILITEM: 'detailItem/FETCH_DETAILITEM',
  CREATE_DETAILITEM: 'detailItem/CREATE_DETAILITEM',
  UPDATE_DETAILITEM: 'detailItem/UPDATE_DETAILITEM',
  PARTIAL_UPDATE_DETAILITEM: 'detailItem/PARTIAL_UPDATE_DETAILITEM',
  DELETE_DETAILITEM: 'detailItem/DELETE_DETAILITEM',
  RESET: 'detailItem/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDetailItem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type DetailItemState = Readonly<typeof initialState>;

// Reducer

export default (state: DetailItemState = initialState, action): DetailItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DETAILITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DETAILITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DETAILITEM):
    case REQUEST(ACTION_TYPES.UPDATE_DETAILITEM):
    case REQUEST(ACTION_TYPES.DELETE_DETAILITEM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_DETAILITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DETAILITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DETAILITEM):
    case FAILURE(ACTION_TYPES.CREATE_DETAILITEM):
    case FAILURE(ACTION_TYPES.UPDATE_DETAILITEM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_DETAILITEM):
    case FAILURE(ACTION_TYPES.DELETE_DETAILITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DETAILITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DETAILITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DETAILITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_DETAILITEM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_DETAILITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DETAILITEM):
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

const apiUrl = 'api/detail-items';

// Actions

export const getEntities: ICrudGetAllAction<IDetailItem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DETAILITEM_LIST,
  payload: axios.get<IDetailItem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IDetailItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DETAILITEM,
    payload: axios.get<IDetailItem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DETAILITEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DETAILITEM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_DETAILITEM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDetailItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DETAILITEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
