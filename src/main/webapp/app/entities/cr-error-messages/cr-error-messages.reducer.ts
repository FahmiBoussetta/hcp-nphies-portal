import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICRErrorMessages, defaultValue } from 'app/shared/model/cr-error-messages.model';

export const ACTION_TYPES = {
  FETCH_CRERRORMESSAGES_LIST: 'cRErrorMessages/FETCH_CRERRORMESSAGES_LIST',
  FETCH_CRERRORMESSAGES: 'cRErrorMessages/FETCH_CRERRORMESSAGES',
  CREATE_CRERRORMESSAGES: 'cRErrorMessages/CREATE_CRERRORMESSAGES',
  UPDATE_CRERRORMESSAGES: 'cRErrorMessages/UPDATE_CRERRORMESSAGES',
  PARTIAL_UPDATE_CRERRORMESSAGES: 'cRErrorMessages/PARTIAL_UPDATE_CRERRORMESSAGES',
  DELETE_CRERRORMESSAGES: 'cRErrorMessages/DELETE_CRERRORMESSAGES',
  RESET: 'cRErrorMessages/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICRErrorMessages>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CRErrorMessagesState = Readonly<typeof initialState>;

// Reducer

export default (state: CRErrorMessagesState = initialState, action): CRErrorMessagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CRERRORMESSAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CRERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CRERRORMESSAGES):
    case REQUEST(ACTION_TYPES.UPDATE_CRERRORMESSAGES):
    case REQUEST(ACTION_TYPES.DELETE_CRERRORMESSAGES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CRERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CRERRORMESSAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CRERRORMESSAGES):
    case FAILURE(ACTION_TYPES.CREATE_CRERRORMESSAGES):
    case FAILURE(ACTION_TYPES.UPDATE_CRERRORMESSAGES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CRERRORMESSAGES):
    case FAILURE(ACTION_TYPES.DELETE_CRERRORMESSAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CRERRORMESSAGES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CRERRORMESSAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CRERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_CRERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CRERRORMESSAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CRERRORMESSAGES):
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

const apiUrl = 'api/cr-error-messages';

// Actions

export const getEntities: ICrudGetAllAction<ICRErrorMessages> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CRERRORMESSAGES_LIST,
  payload: axios.get<ICRErrorMessages>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICRErrorMessages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CRERRORMESSAGES,
    payload: axios.get<ICRErrorMessages>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICRErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CRERRORMESSAGES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICRErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CRERRORMESSAGES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICRErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CRERRORMESSAGES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICRErrorMessages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CRERRORMESSAGES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
