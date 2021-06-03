import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdjudicationSubDetailItem, defaultValue } from 'app/shared/model/adjudication-sub-detail-item.model';

export const ACTION_TYPES = {
  FETCH_ADJUDICATIONSUBDETAILITEM_LIST: 'adjudicationSubDetailItem/FETCH_ADJUDICATIONSUBDETAILITEM_LIST',
  FETCH_ADJUDICATIONSUBDETAILITEM: 'adjudicationSubDetailItem/FETCH_ADJUDICATIONSUBDETAILITEM',
  CREATE_ADJUDICATIONSUBDETAILITEM: 'adjudicationSubDetailItem/CREATE_ADJUDICATIONSUBDETAILITEM',
  UPDATE_ADJUDICATIONSUBDETAILITEM: 'adjudicationSubDetailItem/UPDATE_ADJUDICATIONSUBDETAILITEM',
  PARTIAL_UPDATE_ADJUDICATIONSUBDETAILITEM: 'adjudicationSubDetailItem/PARTIAL_UPDATE_ADJUDICATIONSUBDETAILITEM',
  DELETE_ADJUDICATIONSUBDETAILITEM: 'adjudicationSubDetailItem/DELETE_ADJUDICATIONSUBDETAILITEM',
  RESET: 'adjudicationSubDetailItem/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdjudicationSubDetailItem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AdjudicationSubDetailItemState = Readonly<typeof initialState>;

// Reducer

export default (state: AdjudicationSubDetailItemState = initialState, action): AdjudicationSubDetailItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADJUDICATIONSUBDETAILITEM):
    case REQUEST(ACTION_TYPES.UPDATE_ADJUDICATIONSUBDETAILITEM):
    case REQUEST(ACTION_TYPES.DELETE_ADJUDICATIONSUBDETAILITEM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONSUBDETAILITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILITEM):
    case FAILURE(ACTION_TYPES.CREATE_ADJUDICATIONSUBDETAILITEM):
    case FAILURE(ACTION_TYPES.UPDATE_ADJUDICATIONSUBDETAILITEM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONSUBDETAILITEM):
    case FAILURE(ACTION_TYPES.DELETE_ADJUDICATIONSUBDETAILITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADJUDICATIONSUBDETAILITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_ADJUDICATIONSUBDETAILITEM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONSUBDETAILITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADJUDICATIONSUBDETAILITEM):
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

const apiUrl = 'api/adjudication-sub-detail-items';

// Actions

export const getEntities: ICrudGetAllAction<IAdjudicationSubDetailItem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILITEM_LIST,
  payload: axios.get<IAdjudicationSubDetailItem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAdjudicationSubDetailItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILITEM,
    payload: axios.get<IAdjudicationSubDetailItem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAdjudicationSubDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADJUDICATIONSUBDETAILITEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdjudicationSubDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADJUDICATIONSUBDETAILITEM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAdjudicationSubDetailItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONSUBDETAILITEM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdjudicationSubDetailItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADJUDICATIONSUBDETAILITEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
