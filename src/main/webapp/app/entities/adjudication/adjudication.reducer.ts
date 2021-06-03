import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdjudication, defaultValue } from 'app/shared/model/adjudication.model';

export const ACTION_TYPES = {
  FETCH_ADJUDICATION_LIST: 'adjudication/FETCH_ADJUDICATION_LIST',
  FETCH_ADJUDICATION: 'adjudication/FETCH_ADJUDICATION',
  CREATE_ADJUDICATION: 'adjudication/CREATE_ADJUDICATION',
  UPDATE_ADJUDICATION: 'adjudication/UPDATE_ADJUDICATION',
  PARTIAL_UPDATE_ADJUDICATION: 'adjudication/PARTIAL_UPDATE_ADJUDICATION',
  DELETE_ADJUDICATION: 'adjudication/DELETE_ADJUDICATION',
  RESET: 'adjudication/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdjudication>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AdjudicationState = Readonly<typeof initialState>;

// Reducer

export default (state: AdjudicationState = initialState, action): AdjudicationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADJUDICATION):
    case REQUEST(ACTION_TYPES.UPDATE_ADJUDICATION):
    case REQUEST(ACTION_TYPES.DELETE_ADJUDICATION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATION):
    case FAILURE(ACTION_TYPES.CREATE_ADJUDICATION):
    case FAILURE(ACTION_TYPES.UPDATE_ADJUDICATION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATION):
    case FAILURE(ACTION_TYPES.DELETE_ADJUDICATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADJUDICATION):
    case SUCCESS(ACTION_TYPES.UPDATE_ADJUDICATION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADJUDICATION):
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

const apiUrl = 'api/adjudications';

// Actions

export const getEntities: ICrudGetAllAction<IAdjudication> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ADJUDICATION_LIST,
  payload: axios.get<IAdjudication>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAdjudication> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADJUDICATION,
    payload: axios.get<IAdjudication>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAdjudication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADJUDICATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdjudication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADJUDICATION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAdjudication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdjudication> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADJUDICATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
