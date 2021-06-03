import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRelated, defaultValue } from 'app/shared/model/related.model';

export const ACTION_TYPES = {
  FETCH_RELATED_LIST: 'related/FETCH_RELATED_LIST',
  FETCH_RELATED: 'related/FETCH_RELATED',
  CREATE_RELATED: 'related/CREATE_RELATED',
  UPDATE_RELATED: 'related/UPDATE_RELATED',
  PARTIAL_UPDATE_RELATED: 'related/PARTIAL_UPDATE_RELATED',
  DELETE_RELATED: 'related/DELETE_RELATED',
  RESET: 'related/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRelated>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type RelatedState = Readonly<typeof initialState>;

// Reducer

export default (state: RelatedState = initialState, action): RelatedState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RELATED_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RELATED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RELATED):
    case REQUEST(ACTION_TYPES.UPDATE_RELATED):
    case REQUEST(ACTION_TYPES.DELETE_RELATED):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_RELATED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RELATED_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RELATED):
    case FAILURE(ACTION_TYPES.CREATE_RELATED):
    case FAILURE(ACTION_TYPES.UPDATE_RELATED):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_RELATED):
    case FAILURE(ACTION_TYPES.DELETE_RELATED):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RELATED_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RELATED):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RELATED):
    case SUCCESS(ACTION_TYPES.UPDATE_RELATED):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_RELATED):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RELATED):
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

const apiUrl = 'api/relateds';

// Actions

export const getEntities: ICrudGetAllAction<IRelated> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RELATED_LIST,
  payload: axios.get<IRelated>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IRelated> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RELATED,
    payload: axios.get<IRelated>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRelated> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RELATED,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRelated> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RELATED,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IRelated> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_RELATED,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRelated> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RELATED,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
