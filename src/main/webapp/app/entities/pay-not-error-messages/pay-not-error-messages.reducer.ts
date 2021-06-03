import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPayNotErrorMessages, defaultValue } from 'app/shared/model/pay-not-error-messages.model';

export const ACTION_TYPES = {
  FETCH_PAYNOTERRORMESSAGES_LIST: 'payNotErrorMessages/FETCH_PAYNOTERRORMESSAGES_LIST',
  FETCH_PAYNOTERRORMESSAGES: 'payNotErrorMessages/FETCH_PAYNOTERRORMESSAGES',
  CREATE_PAYNOTERRORMESSAGES: 'payNotErrorMessages/CREATE_PAYNOTERRORMESSAGES',
  UPDATE_PAYNOTERRORMESSAGES: 'payNotErrorMessages/UPDATE_PAYNOTERRORMESSAGES',
  PARTIAL_UPDATE_PAYNOTERRORMESSAGES: 'payNotErrorMessages/PARTIAL_UPDATE_PAYNOTERRORMESSAGES',
  DELETE_PAYNOTERRORMESSAGES: 'payNotErrorMessages/DELETE_PAYNOTERRORMESSAGES',
  RESET: 'payNotErrorMessages/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPayNotErrorMessages>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PayNotErrorMessagesState = Readonly<typeof initialState>;

// Reducer

export default (state: PayNotErrorMessagesState = initialState, action): PayNotErrorMessagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PAYNOTERRORMESSAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PAYNOTERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PAYNOTERRORMESSAGES):
    case REQUEST(ACTION_TYPES.UPDATE_PAYNOTERRORMESSAGES):
    case REQUEST(ACTION_TYPES.DELETE_PAYNOTERRORMESSAGES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PAYNOTERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PAYNOTERRORMESSAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PAYNOTERRORMESSAGES):
    case FAILURE(ACTION_TYPES.CREATE_PAYNOTERRORMESSAGES):
    case FAILURE(ACTION_TYPES.UPDATE_PAYNOTERRORMESSAGES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PAYNOTERRORMESSAGES):
    case FAILURE(ACTION_TYPES.DELETE_PAYNOTERRORMESSAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYNOTERRORMESSAGES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYNOTERRORMESSAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PAYNOTERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_PAYNOTERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PAYNOTERRORMESSAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PAYNOTERRORMESSAGES):
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

const apiUrl = 'api/pay-not-error-messages';

// Actions

export const getEntities: ICrudGetAllAction<IPayNotErrorMessages> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PAYNOTERRORMESSAGES_LIST,
  payload: axios.get<IPayNotErrorMessages>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPayNotErrorMessages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PAYNOTERRORMESSAGES,
    payload: axios.get<IPayNotErrorMessages>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPayNotErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PAYNOTERRORMESSAGES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPayNotErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PAYNOTERRORMESSAGES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPayNotErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PAYNOTERRORMESSAGES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPayNotErrorMessages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PAYNOTERRORMESSAGES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
