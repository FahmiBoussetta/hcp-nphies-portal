import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAckErrorMessages, defaultValue } from 'app/shared/model/ack-error-messages.model';

export const ACTION_TYPES = {
  FETCH_ACKERRORMESSAGES_LIST: 'ackErrorMessages/FETCH_ACKERRORMESSAGES_LIST',
  FETCH_ACKERRORMESSAGES: 'ackErrorMessages/FETCH_ACKERRORMESSAGES',
  CREATE_ACKERRORMESSAGES: 'ackErrorMessages/CREATE_ACKERRORMESSAGES',
  UPDATE_ACKERRORMESSAGES: 'ackErrorMessages/UPDATE_ACKERRORMESSAGES',
  PARTIAL_UPDATE_ACKERRORMESSAGES: 'ackErrorMessages/PARTIAL_UPDATE_ACKERRORMESSAGES',
  DELETE_ACKERRORMESSAGES: 'ackErrorMessages/DELETE_ACKERRORMESSAGES',
  RESET: 'ackErrorMessages/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAckErrorMessages>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AckErrorMessagesState = Readonly<typeof initialState>;

// Reducer

export default (state: AckErrorMessagesState = initialState, action): AckErrorMessagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACKERRORMESSAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACKERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ACKERRORMESSAGES):
    case REQUEST(ACTION_TYPES.UPDATE_ACKERRORMESSAGES):
    case REQUEST(ACTION_TYPES.DELETE_ACKERRORMESSAGES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ACKERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ACKERRORMESSAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACKERRORMESSAGES):
    case FAILURE(ACTION_TYPES.CREATE_ACKERRORMESSAGES):
    case FAILURE(ACTION_TYPES.UPDATE_ACKERRORMESSAGES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ACKERRORMESSAGES):
    case FAILURE(ACTION_TYPES.DELETE_ACKERRORMESSAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACKERRORMESSAGES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACKERRORMESSAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACKERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_ACKERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ACKERRORMESSAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACKERRORMESSAGES):
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

const apiUrl = 'api/ack-error-messages';

// Actions

export const getEntities: ICrudGetAllAction<IAckErrorMessages> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ACKERRORMESSAGES_LIST,
  payload: axios.get<IAckErrorMessages>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAckErrorMessages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACKERRORMESSAGES,
    payload: axios.get<IAckErrorMessages>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAckErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACKERRORMESSAGES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAckErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACKERRORMESSAGES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAckErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ACKERRORMESSAGES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAckErrorMessages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACKERRORMESSAGES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
