import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IReconciliationDetailItem, defaultValue } from 'app/shared/model/reconciliation-detail-item.model';

export const ACTION_TYPES = {
  FETCH_RECONCILIATIONDETAILITEM_LIST: 'reconciliationDetailItem/FETCH_RECONCILIATIONDETAILITEM_LIST',
  FETCH_RECONCILIATIONDETAILITEM: 'reconciliationDetailItem/FETCH_RECONCILIATIONDETAILITEM',
  CREATE_RECONCILIATIONDETAILITEM: 'reconciliationDetailItem/CREATE_RECONCILIATIONDETAILITEM',
  UPDATE_RECONCILIATIONDETAILITEM: 'reconciliationDetailItem/UPDATE_RECONCILIATIONDETAILITEM',
  PARTIAL_UPDATE_RECONCILIATIONDETAILITEM: 'reconciliationDetailItem/PARTIAL_UPDATE_RECONCILIATIONDETAILITEM',
  DELETE_RECONCILIATIONDETAILITEM: 'reconciliationDetailItem/DELETE_RECONCILIATIONDETAILITEM',
  RESET: 'reconciliationDetailItem/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IReconciliationDetailItem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ReconciliationDetailItemState = Readonly<typeof initialState>;

// Reducer

export default (state: ReconciliationDetailItemState = initialState, action): ReconciliationDetailItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RECONCILIATIONDETAILITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RECONCILIATIONDETAILITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RECONCILIATIONDETAILITEM):
    case REQUEST(ACTION_TYPES.UPDATE_RECONCILIATIONDETAILITEM):
    case REQUEST(ACTION_TYPES.DELETE_RECONCILIATIONDETAILITEM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_RECONCILIATIONDETAILITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RECONCILIATIONDETAILITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RECONCILIATIONDETAILITEM):
    case FAILURE(ACTION_TYPES.CREATE_RECONCILIATIONDETAILITEM):
    case FAILURE(ACTION_TYPES.UPDATE_RECONCILIATIONDETAILITEM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_RECONCILIATIONDETAILITEM):
    case FAILURE(ACTION_TYPES.DELETE_RECONCILIATIONDETAILITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECONCILIATIONDETAILITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECONCILIATIONDETAILITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RECONCILIATIONDETAILITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_RECONCILIATIONDETAILITEM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_RECONCILIATIONDETAILITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RECONCILIATIONDETAILITEM):
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

const apiUrl = 'api/reconciliation-detail-items';

// Actions

export const getEntities: ICrudGetAllAction<IReconciliationDetailItem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RECONCILIATIONDETAILITEM_LIST,
  payload: axios.get<IReconciliationDetailItem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IReconciliationDetailItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RECONCILIATIONDETAILITEM,
    payload: axios.get<IReconciliationDetailItem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IReconciliationDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RECONCILIATIONDETAILITEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IReconciliationDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RECONCILIATIONDETAILITEM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IReconciliationDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_RECONCILIATIONDETAILITEM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IReconciliationDetailItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RECONCILIATIONDETAILITEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
