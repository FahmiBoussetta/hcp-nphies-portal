import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISubDetailItem, defaultValue } from 'app/shared/model/sub-detail-item.model';

export const ACTION_TYPES = {
  FETCH_SUBDETAILITEM_LIST: 'subDetailItem/FETCH_SUBDETAILITEM_LIST',
  FETCH_SUBDETAILITEM: 'subDetailItem/FETCH_SUBDETAILITEM',
  CREATE_SUBDETAILITEM: 'subDetailItem/CREATE_SUBDETAILITEM',
  UPDATE_SUBDETAILITEM: 'subDetailItem/UPDATE_SUBDETAILITEM',
  PARTIAL_UPDATE_SUBDETAILITEM: 'subDetailItem/PARTIAL_UPDATE_SUBDETAILITEM',
  DELETE_SUBDETAILITEM: 'subDetailItem/DELETE_SUBDETAILITEM',
  RESET: 'subDetailItem/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISubDetailItem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SubDetailItemState = Readonly<typeof initialState>;

// Reducer

export default (state: SubDetailItemState = initialState, action): SubDetailItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SUBDETAILITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUBDETAILITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SUBDETAILITEM):
    case REQUEST(ACTION_TYPES.UPDATE_SUBDETAILITEM):
    case REQUEST(ACTION_TYPES.DELETE_SUBDETAILITEM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SUBDETAILITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SUBDETAILITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUBDETAILITEM):
    case FAILURE(ACTION_TYPES.CREATE_SUBDETAILITEM):
    case FAILURE(ACTION_TYPES.UPDATE_SUBDETAILITEM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SUBDETAILITEM):
    case FAILURE(ACTION_TYPES.DELETE_SUBDETAILITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBDETAILITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBDETAILITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUBDETAILITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_SUBDETAILITEM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SUBDETAILITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUBDETAILITEM):
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

const apiUrl = 'api/sub-detail-items';

// Actions

export const getEntities: ICrudGetAllAction<ISubDetailItem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SUBDETAILITEM_LIST,
  payload: axios.get<ISubDetailItem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISubDetailItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUBDETAILITEM,
    payload: axios.get<ISubDetailItem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISubDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUBDETAILITEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISubDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUBDETAILITEM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISubDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SUBDETAILITEM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISubDetailItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUBDETAILITEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
