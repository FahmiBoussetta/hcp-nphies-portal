import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAdjudicationItem } from 'app/shared/model/adjudication-item.model';
import { getEntities as getAdjudicationItems } from 'app/entities/adjudication-item/adjudication-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adjudication-notes.reducer';
import { IAdjudicationNotes } from 'app/shared/model/adjudication-notes.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAdjudicationNotesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdjudicationNotesUpdate = (props: IAdjudicationNotesUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { adjudicationNotesEntity, adjudicationItems, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/adjudication-notes');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAdjudicationItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...adjudicationNotesEntity,
        ...values,
        adjudicationItem: adjudicationItems.find(it => it.id.toString() === values.adjudicationItemId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
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
            <AvForm model={isNew ? {} : adjudicationNotesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="adjudication-notes-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="adjudication-notes-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="noteLabel" for="adjudication-notes-note">
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.note">Note</Translate>
                </Label>
                <AvField id="adjudication-notes-note" data-cy="note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label for="adjudication-notes-adjudicationItem">
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.adjudicationItem">Adjudication Item</Translate>
                </Label>
                <AvInput
                  id="adjudication-notes-adjudicationItem"
                  data-cy="adjudicationItem"
                  type="select"
                  className="form-control"
                  name="adjudicationItemId"
                >
                  <option value="" key="0" />
                  {adjudicationItems
                    ? adjudicationItems.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/adjudication-notes" replace color="info">
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
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  adjudicationItems: storeState.adjudicationItem.entities,
  adjudicationNotesEntity: storeState.adjudicationNotes.entity,
  loading: storeState.adjudicationNotes.loading,
  updating: storeState.adjudicationNotes.updating,
  updateSuccess: storeState.adjudicationNotes.updateSuccess,
});

const mapDispatchToProps = {
  getAdjudicationItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjudicationNotesUpdate);
