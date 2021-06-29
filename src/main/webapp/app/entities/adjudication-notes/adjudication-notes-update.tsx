import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAdjudicationItem } from 'app/shared/model/adjudication-item.model';
import { getEntities as getAdjudicationItems } from 'app/entities/adjudication-item/adjudication-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adjudication-notes.reducer';
import { IAdjudicationNotes } from 'app/shared/model/adjudication-notes.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationNotesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const adjudicationItems = useAppSelector(state => state.adjudicationItem.entities);
  const adjudicationNotesEntity = useAppSelector(state => state.adjudicationNotes.entity);
  const loading = useAppSelector(state => state.adjudicationNotes.loading);
  const updating = useAppSelector(state => state.adjudicationNotes.updating);
  const updateSuccess = useAppSelector(state => state.adjudicationNotes.updateSuccess);

  const handleClose = () => {
    props.history.push('/adjudication-notes');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAdjudicationItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...adjudicationNotesEntity,
      ...values,
      adjudicationItem: adjudicationItems.find(it => it.id.toString() === values.adjudicationItemId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...adjudicationNotesEntity,
          adjudicationItemId: adjudicationNotesEntity?.adjudicationItem?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.adjudicationNotes.home.createOrEditLabel" data-cy="AdjudicationNotesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.home.createOrEditLabel">
              Create or edit a AdjudicationNotes
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="adjudication-notes-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.adjudicationNotes.note')}
                id="adjudication-notes-note"
                name="note"
                data-cy="note"
                type="text"
              />
              <ValidatedField
                id="adjudication-notes-adjudicationItem"
                name="adjudicationItemId"
                data-cy="adjudicationItem"
                label={translate('hcpNphiesPortalApp.adjudicationNotes.adjudicationItem')}
                type="select"
              >
                <option value="" key="0" />
                {adjudicationItems
                  ? adjudicationItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/adjudication-notes" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AdjudicationNotesUpdate;
