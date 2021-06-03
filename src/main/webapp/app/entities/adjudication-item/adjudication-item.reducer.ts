import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdjudicationItem, defaultValue } from 'app/shared/model/adjudication-item.model';

export const ACTION_TYPES = {
  FETCH_ADJUDICATIONITEM_LIST: 'adjudicationItem/FETCH_ADJUDICATIONITEM_LIST',
  FETCH_ADJUDICATIONITEM: 'adjudicationItem/FETCH_ADJUDICATIONITEM',
  CREATE_ADJUDICATIONITEM: 'adjudicationItem/CREATE_ADJUDICATIONITEM',
  UPDATE_ADJUDICATIONITEM: 'adjudicationItem/UPDATE_ADJUDICATIONITEM',
  PARTIAL_UPDATE_ADJUDICATIONITEM: 'adjudicationItem/PARTIAL_UPDATE_ADJUDICATIONITEM',
  DELETE_ADJUDICATIONITEM: 'adjudicationItem/DELETE_ADJUDICATIONITEM',
  RESET: 'adjudicationItem/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdjudicationItem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AdjudicationItemState = Readonly<typeof initialState>;

// Reducer

export default (state: AdjudicationItemState = initialState, action): AdjudicationItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATIONITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATIONITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADJUDICATIONITEM):
    case REQUEST(ACTION_TYPES.UPDATE_ADJUDICATIONITEM):
    case REQUEST(ACTION_TYPES.DELETE_ADJUDICATIONITEM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATIONITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATIONITEM):
    case FAILURE(ACTION_TYPES.CREATE_ADJUDICATIONITEM):
    case FAILURE(ACTION_TYPES.UPDATE_ADJUDICATIONITEM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONITEM):
    case FAILURE(ACTION_TYPES.DELETE_ADJUDICATIONITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATIONITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATIONITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADJUDICATIONITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_ADJUDICATIONITEM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADJUDICATIONITEM):
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

const apiUrl = 'api/adjudication-items';

// Actions

export const getEntities: ICrudGetAllAction<IAdjudicationItem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ADJUDICATIONITEM_LIST,
  payload: axios.get<IAdjudicationItem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAdjudicationItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADJUDICATIONITEM,
    payload: axios.get<IAdjudicationItem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAdjudicationItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADJUDICATIONITEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdjudicationItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADJUDICATIONITEM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAdjudicationItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONITEM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdjudicationItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADJUDICATIONITEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
