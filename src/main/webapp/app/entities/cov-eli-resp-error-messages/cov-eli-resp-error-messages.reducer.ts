import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICovEliRespErrorMessages, defaultValue } from 'app/shared/model/cov-eli-resp-error-messages.model';

export const ACTION_TYPES = {
  FETCH_COVELIRESPERRORMESSAGES_LIST: 'covEliRespErrorMessages/FETCH_COVELIRESPERRORMESSAGES_LIST',
  FETCH_COVELIRESPERRORMESSAGES: 'covEliRespErrorMessages/FETCH_COVELIRESPERRORMESSAGES',
  CREATE_COVELIRESPERRORMESSAGES: 'covEliRespErrorMessages/CREATE_COVELIRESPERRORMESSAGES',
  UPDATE_COVELIRESPERRORMESSAGES: 'covEliRespErrorMessages/UPDATE_COVELIRESPERRORMESSAGES',
  PARTIAL_UPDATE_COVELIRESPERRORMESSAGES: 'covEliRespErrorMessages/PARTIAL_UPDATE_COVELIRESPERRORMESSAGES',
  DELETE_COVELIRESPERRORMESSAGES: 'covEliRespErrorMessages/DELETE_COVELIRESPERRORMESSAGES',
  RESET: 'covEliRespErrorMessages/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICovEliRespErrorMessages>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CovEliRespErrorMessagesState = Readonly<typeof initialState>;

// Reducer

export default (state: CovEliRespErrorMessagesState = initialState, action): CovEliRespErrorMessagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COVELIRESPERRORMESSAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COVELIRESPERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COVELIRESPERRORMESSAGES):
    case REQUEST(ACTION_TYPES.UPDATE_COVELIRESPERRORMESSAGES):
    case REQUEST(ACTION_TYPES.DELETE_COVELIRESPERRORMESSAGES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COVELIRESPERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COVELIRESPERRORMESSAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COVELIRESPERRORMESSAGES):
    case FAILURE(ACTION_TYPES.CREATE_COVELIRESPERRORMESSAGES):
    case FAILURE(ACTION_TYPES.UPDATE_COVELIRESPERRORMESSAGES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COVELIRESPERRORMESSAGES):
    case FAILURE(ACTION_TYPES.DELETE_COVELIRESPERRORMESSAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COVELIRESPERRORMESSAGES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COVELIRESPERRORMESSAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COVELIRESPERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_COVELIRESPERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COVELIRESPERRORMESSAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COVELIRESPERRORMESSAGES):
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

const apiUrl = 'api/cov-eli-resp-error-messages';

// Actions

export const getEntities: ICrudGetAllAction<ICovEliRespErrorMessages> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COVELIRESPERRORMESSAGES_LIST,
  payload: axios.get<ICovEliRespErrorMessages>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICovEliRespErrorMessages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COVELIRESPERRORMESSAGES,
    payload: axios.get<ICovEliRespErrorMessages>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICovEliRespErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COVELIRESPERRORMESSAGES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICovEliRespErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COVELIRESPERRORMESSAGES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICovEliRespErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COVELIRESPERRORMESSAGES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICovEliRespErrorMessages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COVELIRESPERRORMESSAGES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
