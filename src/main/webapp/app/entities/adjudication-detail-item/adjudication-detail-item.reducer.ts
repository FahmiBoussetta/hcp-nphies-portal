import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdjudicationDetailItem, defaultValue } from 'app/shared/model/adjudication-detail-item.model';

export const ACTION_TYPES = {
  FETCH_ADJUDICATIONDETAILITEM_LIST: 'adjudicationDetailItem/FETCH_ADJUDICATIONDETAILITEM_LIST',
  FETCH_ADJUDICATIONDETAILITEM: 'adjudicationDetailItem/FETCH_ADJUDICATIONDETAILITEM',
  CREATE_ADJUDICATIONDETAILITEM: 'adjudicationDetailItem/CREATE_ADJUDICATIONDETAILITEM',
  UPDATE_ADJUDICATIONDETAILITEM: 'adjudicationDetailItem/UPDATE_ADJUDICATIONDETAILITEM',
  PARTIAL_UPDATE_ADJUDICATIONDETAILITEM: 'adjudicationDetailItem/PARTIAL_UPDATE_ADJUDICATIONDETAILITEM',
  DELETE_ADJUDICATIONDETAILITEM: 'adjudicationDetailItem/DELETE_ADJUDICATIONDETAILITEM',
  RESET: 'adjudicationDetailItem/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdjudicationDetailItem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AdjudicationDetailItemState = Readonly<typeof initialState>;

// Reducer

export default (state: AdjudicationDetailItemState = initialState, action): AdjudicationDetailItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATIONDETAILITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATIONDETAILITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADJUDICATIONDETAILITEM):
    case REQUEST(ACTION_TYPES.UPDATE_ADJUDICATIONDETAILITEM):
    case REQUEST(ACTION_TYPES.DELETE_ADJUDICATIONDETAILITEM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONDETAILITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATIONDETAILITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATIONDETAILITEM):
    case FAILURE(ACTION_TYPES.CREATE_ADJUDICATIONDETAILITEM):
    case FAILURE(ACTION_TYPES.UPDATE_ADJUDICATIONDETAILITEM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONDETAILITEM):
    case FAILURE(ACTION_TYPES.DELETE_ADJUDICATIONDETAILITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATIONDETAILITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATIONDETAILITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADJUDICATIONDETAILITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_ADJUDICATIONDETAILITEM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONDETAILITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADJUDICATIONDETAILITEM):
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

const apiUrl = 'api/adjudication-detail-items';

// Actions

export const getEntities: ICrudGetAllAction<IAdjudicationDetailItem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ADJUDICATIONDETAILITEM_LIST,
  payload: axios.get<IAdjudicationDetailItem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAdjudicationDetailItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADJUDICATIONDETAILITEM,
    payload: axios.get<IAdjudicationDetailItem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAdjudicationDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADJUDICATIONDETAILITEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdjudicationDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADJUDICATIONDETAILITEM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAdjudicationDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONDETAILITEM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdjudicationDetailItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADJUDICATIONDETAILITEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
