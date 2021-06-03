import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClaimErrorMessages, defaultValue } from 'app/shared/model/claim-error-messages.model';

export const ACTION_TYPES = {
  FETCH_CLAIMERRORMESSAGES_LIST: 'claimErrorMessages/FETCH_CLAIMERRORMESSAGES_LIST',
  FETCH_CLAIMERRORMESSAGES: 'claimErrorMessages/FETCH_CLAIMERRORMESSAGES',
  CREATE_CLAIMERRORMESSAGES: 'claimErrorMessages/CREATE_CLAIMERRORMESSAGES',
  UPDATE_CLAIMERRORMESSAGES: 'claimErrorMessages/UPDATE_CLAIMERRORMESSAGES',
  PARTIAL_UPDATE_CLAIMERRORMESSAGES: 'claimErrorMessages/PARTIAL_UPDATE_CLAIMERRORMESSAGES',
  DELETE_CLAIMERRORMESSAGES: 'claimErrorMessages/DELETE_CLAIMERRORMESSAGES',
  RESET: 'claimErrorMessages/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClaimErrorMessages>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ClaimErrorMessagesState = Readonly<typeof initialState>;

// Reducer

export default (state: ClaimErrorMessagesState = initialState, action): ClaimErrorMessagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLAIMERRORMESSAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLAIMERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CLAIMERRORMESSAGES):
    case REQUEST(ACTION_TYPES.UPDATE_CLAIMERRORMESSAGES):
    case REQUEST(ACTION_TYPES.DELETE_CLAIMERRORMESSAGES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CLAIMERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CLAIMERRORMESSAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLAIMERRORMESSAGES):
    case FAILURE(ACTION_TYPES.CREATE_CLAIMERRORMESSAGES):
    case FAILURE(ACTION_TYPES.UPDATE_CLAIMERRORMESSAGES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CLAIMERRORMESSAGES):
    case FAILURE(ACTION_TYPES.DELETE_CLAIMERRORMESSAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLAIMERRORMESSAGES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLAIMERRORMESSAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLAIMERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_CLAIMERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CLAIMERRORMESSAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLAIMERRORMESSAGES):
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

const apiUrl = 'api/claim-error-messages';

// Actions

export const getEntities: ICrudGetAllAction<IClaimErrorMessages> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CLAIMERRORMESSAGES_LIST,
  payload: axios.get<IClaimErrorMessages>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IClaimErrorMessages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLAIMERRORMESSAGES,
    payload: axios.get<IClaimErrorMessages>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IClaimErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLAIMERRORMESSAGES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClaimErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLAIMERRORMESSAGES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IClaimErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CLAIMERRORMESSAGES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClaimErrorMessages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLAIMERRORMESSAGES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
