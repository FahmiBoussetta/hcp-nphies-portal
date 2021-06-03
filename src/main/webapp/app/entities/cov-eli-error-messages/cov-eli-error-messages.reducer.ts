import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICovEliErrorMessages, defaultValue } from 'app/shared/model/cov-eli-error-messages.model';

export const ACTION_TYPES = {
  FETCH_COVELIERRORMESSAGES_LIST: 'covEliErrorMessages/FETCH_COVELIERRORMESSAGES_LIST',
  FETCH_COVELIERRORMESSAGES: 'covEliErrorMessages/FETCH_COVELIERRORMESSAGES',
  CREATE_COVELIERRORMESSAGES: 'covEliErrorMessages/CREATE_COVELIERRORMESSAGES',
  UPDATE_COVELIERRORMESSAGES: 'covEliErrorMessages/UPDATE_COVELIERRORMESSAGES',
  PARTIAL_UPDATE_COVELIERRORMESSAGES: 'covEliErrorMessages/PARTIAL_UPDATE_COVELIERRORMESSAGES',
  DELETE_COVELIERRORMESSAGES: 'covEliErrorMessages/DELETE_COVELIERRORMESSAGES',
  RESET: 'covEliErrorMessages/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICovEliErrorMessages>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CovEliErrorMessagesState = Readonly<typeof initialState>;

// Reducer

export default (state: CovEliErrorMessagesState = initialState, action): CovEliErrorMessagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COVELIERRORMESSAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COVELIERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COVELIERRORMESSAGES):
    case REQUEST(ACTION_TYPES.UPDATE_COVELIERRORMESSAGES):
    case REQUEST(ACTION_TYPES.DELETE_COVELIERRORMESSAGES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COVELIERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COVELIERRORMESSAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COVELIERRORMESSAGES):
    case FAILURE(ACTION_TYPES.CREATE_COVELIERRORMESSAGES):
    case FAILURE(ACTION_TYPES.UPDATE_COVELIERRORMESSAGES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COVELIERRORMESSAGES):
    case FAILURE(ACTION_TYPES.DELETE_COVELIERRORMESSAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COVELIERRORMESSAGES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COVELIERRORMESSAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COVELIERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_COVELIERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COVELIERRORMESSAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COVELIERRORMESSAGES):
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

const apiUrl = 'api/cov-eli-error-messages';

// Actions

export const getEntities: ICrudGetAllAction<ICovEliErrorMessages> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COVELIERRORMESSAGES_LIST,
  payload: axios.get<ICovEliErrorMessages>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICovEliErrorMessages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COVELIERRORMESSAGES,
    payload: axios.get<ICovEliErrorMessages>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICovEliErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COVELIERRORMESSAGES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICovEliErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COVELIERRORMESSAGES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICovEliErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COVELIERRORMESSAGES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICovEliErrorMessages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COVELIERRORMESSAGES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
