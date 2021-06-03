import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IComErrorMessages, defaultValue } from 'app/shared/model/com-error-messages.model';

export const ACTION_TYPES = {
  FETCH_COMERRORMESSAGES_LIST: 'comErrorMessages/FETCH_COMERRORMESSAGES_LIST',
  FETCH_COMERRORMESSAGES: 'comErrorMessages/FETCH_COMERRORMESSAGES',
  CREATE_COMERRORMESSAGES: 'comErrorMessages/CREATE_COMERRORMESSAGES',
  UPDATE_COMERRORMESSAGES: 'comErrorMessages/UPDATE_COMERRORMESSAGES',
  PARTIAL_UPDATE_COMERRORMESSAGES: 'comErrorMessages/PARTIAL_UPDATE_COMERRORMESSAGES',
  DELETE_COMERRORMESSAGES: 'comErrorMessages/DELETE_COMERRORMESSAGES',
  RESET: 'comErrorMessages/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IComErrorMessages>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ComErrorMessagesState = Readonly<typeof initialState>;

// Reducer

export default (state: ComErrorMessagesState = initialState, action): ComErrorMessagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMERRORMESSAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COMERRORMESSAGES):
    case REQUEST(ACTION_TYPES.UPDATE_COMERRORMESSAGES):
    case REQUEST(ACTION_TYPES.DELETE_COMERRORMESSAGES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COMERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COMERRORMESSAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMERRORMESSAGES):
    case FAILURE(ACTION_TYPES.CREATE_COMERRORMESSAGES):
    case FAILURE(ACTION_TYPES.UPDATE_COMERRORMESSAGES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COMERRORMESSAGES):
    case FAILURE(ACTION_TYPES.DELETE_COMERRORMESSAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMERRORMESSAGES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMERRORMESSAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_COMERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COMERRORMESSAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMERRORMESSAGES):
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

const apiUrl = 'api/com-error-messages';

// Actions

export const getEntities: ICrudGetAllAction<IComErrorMessages> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COMERRORMESSAGES_LIST,
  payload: axios.get<IComErrorMessages>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IComErrorMessages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMERRORMESSAGES,
    payload: axios.get<IComErrorMessages>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IComErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMERRORMESSAGES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IComErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMERRORMESSAGES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IComErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COMERRORMESSAGES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IComErrorMessages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMERRORMESSAGES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
